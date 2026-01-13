package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.entities.User
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase

fun main() {
    createSessionFactory().use { sessionFactory ->
        sessionFactory.fillDatabase()

        var user: User? = null
        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            user = session.find(User::class.java, 1)
            user.company.name
            user.userChats.size

            val user1 = session.find(User::class.java, 1)
            user1.company.name
            user1.userChats.size


            session.transaction.commit()
        }

        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val user2 = session.find(User::class.java, 1)
            user2.company.name
            user2.userChats.size

            session.transaction.commit()
        }
    }
}