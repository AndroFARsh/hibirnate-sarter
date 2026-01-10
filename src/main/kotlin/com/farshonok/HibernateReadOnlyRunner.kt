package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.hibernate.LockMode
import org.hibernate.jpa.AvailableHints
import org.hibernate.jpa.LegacySpecHints.HINT_JAVAEE_LOCK_TIMEOUT
import org.hibernate.jpa.QueryHints

fun main() {
    createSessionFactory().use { sessionFactory ->
        sessionFactory.fillDatabase()
        sessionFactory.openSession().use { session ->
            // set read only for all entities app level
            session.isDefaultReadOnly = true
            // set read only for Payment entity app level
//            session.setReadOnly(Payment::class.java, true)
            session.beginTransaction()

            val payments = session.createQuery("select p from Payment p", Payment::class.java)
                //.setReadOnly(true)
                //.setHint(AvailableHints.HINT_READ_ONLY, true)
                .list()


            val payment = session.find(Payment::class.java, 1)
            payment.amount += 10

            session.transaction.commit()
        }
    }
}