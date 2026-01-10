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
//            session.doWork { connection -> connection.autoCommit = false }

            val payments = session.createQuery("select p from Payment p", Payment::class.java)
                .list()


            val payment = session.find(Payment::class.java, 1)
            payment.amount += 10

            // exception no transaction is in progress
            session.flush()
        }
    }
}