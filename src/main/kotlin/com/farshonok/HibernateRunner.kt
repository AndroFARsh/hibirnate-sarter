package com.farshonok

import com.farshonok.dao.CriteriaApiUserDao
import com.farshonok.dao.HQLUserDao
import com.farshonok.dao.QueryDslUserDao
import com.farshonok.utils.createSessionFactory

fun main() {
    createSessionFactory().use { sessionFactory ->
//    sessionFactory.fillDatabase()

    val userDao = QueryDslUserDao()

    sessionFactory.openSession().use { session ->
        session.beginTransaction()

        val users = userDao.findAll(session)

        println(">>>>>>> PAYMENTS <<<<<<<<<")
        users.forEach { user -> println(user.payments.size) }

        println(">>>>>>> CHATS <<<<<<<<<")
        users.forEach { user -> println(user.userChats.size) }

        session.transaction.commit()
    }}
}