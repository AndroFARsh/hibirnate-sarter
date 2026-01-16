package com.farshonok

import com.farshonok.repositories.PaymentRepository
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.proxyCurrentSession

fun main() {
    createSessionFactory().use { sessionFactory ->
        //sessionFactory.fillDatabase()

        val session = sessionFactory.proxyCurrentSession()
        val paymentRepository = PaymentRepository(session)

        session.beginTransaction()

        paymentRepository.findById(1L).ifPresent {
            println(it)
        }

        println(paymentRepository.findById(100L).isEmpty)

        session.transaction.commit()
    }
}