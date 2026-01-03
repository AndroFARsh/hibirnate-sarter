package com.farshonok

import com.farshonok.dao.QueryDslUserDao
import com.farshonok.entities.User
import com.farshonok.entities.createGraphWithCompanyAndChats
import com.farshonok.utils.createSessionFactory
import org.hibernate.jpa.AvailableHints

fun main() {
    createSessionFactory().use { sessionFactory ->
//    sessionFactory.fillDatabase()

        val userDao = QueryDslUserDao()

        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val graph = session.getEntityGraph("GraphWithCompanyAndChats")
            val user1 = session.find(
                User::class.java, 2, mapOf(
                    AvailableHints.HINT_SPEC_LOAD_GRAPH to graph
                )
            )
            println(">>>>>>> USER GRAPH: ${user1.userChats.size} <<<<<<<<<")
            user1.userChats.forEach { println(it.chat.name) }

            println("++++++++++++++++++++++++++++++++++++++")

            val users1 = session.createQuery(
                """
            select u from User u 
            where id=:userId
        """, User::class.java
            )
                .apply {
                    setHint(AvailableHints.HINT_SPEC_LOAD_GRAPH, graph)
                    setParameter("userId", 2)
                }
                .list()
            println(">>>>>>>  USER GRAPH: ${users1.size} <<<<<<<<<")
            users1.forEach { u -> u.userChats.forEach { println(it.chat.name) } }


            println("|||||||||||||||||||||||||||||||||||||")

            val users2 = session.createQuery(
                """
            select u from User u 
            where id=:userId
        """, User::class.java
            )
                .apply {
                    setHint(AvailableHints.HINT_SPEC_LOAD_GRAPH, session.createGraphWithCompanyAndChats())
                    setParameter("userId", 2)
                }
                .list()
            println(">>>>>>>  USER GRAPH: ${users2.size} <<<<<<<<<")
            users2.forEach { u -> u.userChats.forEach { println(it.chat.name) } }



            session.enableFetchProfile("ProfileWithCompanyAndChats")

            val user = session.find(User::class.java, 2)
            println(">>>>>>> USER PROFILE: ${user.userChats.size} <<<<<<<<<")
            user.userChats.forEach { println(it.chat.name) }

            session.disableFetchProfile("ProfileWithCompanyAndChats")


            val users = userDao.findAll(session)

            println(">>>>>>> PAYMENTS <<<<<<<<<")
            users.forEach { user -> println(user.payments.size) }

            println(">>>>>>> CHATS <<<<<<<<<")
            users.forEach { user -> println(user.userChats.size) }

            session.transaction.commit()
        }
    }
}