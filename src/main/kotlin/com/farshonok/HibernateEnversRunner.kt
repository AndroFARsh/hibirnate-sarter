package com.farshonok

import com.farshonok.entities.AuditableEntity
import com.farshonok.entities.AuditableEntity_
import com.farshonok.entities.Payment
import com.farshonok.entities.Payment_
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase
import org.hibernate.ReplicationMode
import org.hibernate.envers.AuditReaderFactory
import org.hibernate.envers.query.AuditEntity

fun main() {
    createSessionFactory().use { sessionFactory ->
        sessionFactory.fillDatabase()
        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val payment = session.find(Payment::class.java, 1)
            payment.amount += 10

            session.transaction.commit()
        }
        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val auditReader = AuditReaderFactory.get(session)

            //auditReader.find(Payment::class.java, 1, Date(1768262148904))
            val oldPayment = auditReader.find(Payment::class.java, 1, 1)

            println(oldPayment)

            val result = auditReader.createQuery()
                .forEntitiesAtRevision(Payment::class.java, 1000)
                .add(AuditEntity.property(Payment_.AMOUNT).ge(200))
                .addProjection(AuditEntity.property(Payment_.AMOUNT))
                .addProjection(AuditEntity.property(Payment_.ID))
                .resultList

            println(result)

            // revert to an old version of payment
            session.replicate(oldPayment, ReplicationMode.OVERWRITE)

            session.transaction.commit()
        }
    }
}