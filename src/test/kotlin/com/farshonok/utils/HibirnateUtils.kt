package com.farshonok.com.farshonok.utils

import com.farshonok.utils.createSessionFactory
import org.hibernate.SessionFactory
import org.testcontainers.containers.PostgreSQLContainer


fun createTestSessionFactory(): SessionFactory {
    val postgres: PostgreSQLContainer<*> by lazy {
        PostgreSQLContainer("postgres:13").apply { start() }
    }

    return createSessionFactory {
        setProperty("hibernate.connection.url", postgres.jdbcUrl)
        setProperty("hibernate.connection.user", postgres.username)
        setProperty("hibernate.connection.password", postgres.password)
    }
}