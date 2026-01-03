package com.farshonok

import com.farshonok.utils.createSessionFactory

fun main() {
    createSessionFactory().use { sessionFactory ->
//    sessionFactory.fillDatabase()
        sessionFactory.openSession().use { session ->
            session.beginTransaction()



            session.transaction.commit()
        }
    }
}