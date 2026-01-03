package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase
import org.hibernate.LockMode

fun main() {
    createSessionFactory().use { sessionFactory ->
        sessionFactory.fillDatabase()
        sessionFactory.openSession().use { session1 ->
        sessionFactory.openSession().use { session2 ->
            session1.beginTransaction()
            session2.beginTransaction()

            val payment = session1.find(Payment::class.java, 1, LockMode.OPTIMISTIC)
            payment.amount += 10

            val samePayment = session2.find(Payment::class.java, 1, LockMode.OPTIMISTIC)
            samePayment.amount += 100

            session1.transaction.commit()
            session2.transaction.commit()
        }}
    }
}