package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase
import jakarta.persistence.LockModeType
import org.hibernate.LockMode
import org.hibernate.jpa.LegacySpecHints.HINT_JAVAEE_LOCK_TIMEOUT
import org.hibernate.jpa.SpecHints.HINT_SPEC_LOCK_TIMEOUT

fun main() {
    createSessionFactory().use { sessionFactory ->
        sessionFactory.fillDatabase()
        sessionFactory.openSession().use { session1 ->
        sessionFactory.openSession().use { session2 ->
            session1.beginTransaction()
            session2.beginTransaction()

            val payments = session1.createQuery("select p from Payment p", Payment::class.java)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
//                .setHint(HINT_JAVAEE_LOCK_TIMEOUT, 3000)
                .setHint(HINT_SPEC_LOCK_TIMEOUT, 2000)
                .list()


            val payment = session1.find(Payment::class.java, 1, LockModeType.PESSIMISTIC_READ)
            payment.amount += 10

            val samePayment = session2.find(Payment::class.java, 1)
            samePayment.amount += 100

            session2.transaction.commit()
            session1.transaction.commit()
        }}
    }
}