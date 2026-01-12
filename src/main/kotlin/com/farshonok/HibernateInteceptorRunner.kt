package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.interceptor.SessionInterceptor
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase

fun main() {
    createSessionFactory().use { sessionFactory ->
        sessionFactory.fillDatabase()
        sessionFactory
            .withOptions()
            .interceptor(SessionInterceptor())
            .openSession().use { session ->
            session.beginTransaction()

            val payment = session.find(Payment::class.java, 1)
            payment.amount += 10

            session.transaction.commit()
        }
    }
}