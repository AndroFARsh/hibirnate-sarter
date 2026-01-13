package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.entities.User
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase

fun main() {
    createSessionFactory().use { sessionFactory ->
//        sessionFactory.fillDatabase()

        val payments: List<Payment> = emptyList()
        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val payments = session.createQuery("select p from Payment p where p.receiver.id = :user_id", Payment::class.java)
                    .setParameter("user_id", 1)
                    .setCacheable(true)
                    .list()
            payments.size
            val payments1 = session.createQuery("select p from Payment p where p.receiver.id = :user_id", Payment::class.java)
                    .setParameter("user_id", 1)
                    .setCacheable(true)
                    .list()

            payments1.size


            session.transaction.commit()
        }

        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val payments2 = session.createQuery("select p from Payment p where p.receiver.id = :user_id", Payment::class.java)
                    .setParameter("user_id", 1)
                    .setCacheable(true)
                    .list()
            payments2.size

            session.transaction.commit()
        }
    }
}