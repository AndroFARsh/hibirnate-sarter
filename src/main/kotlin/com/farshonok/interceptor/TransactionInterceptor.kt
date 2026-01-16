package com.farshonok.interceptor

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction
import jakarta.transaction.Transactional
import net.bytebuddy.implementation.bind.annotation.Origin
import net.bytebuddy.implementation.bind.annotation.RuntimeType
import net.bytebuddy.implementation.bind.annotation.SuperCall
import java.lang.reflect.Method
import java.util.concurrent.Callable

class TransactionInterceptor(
    val entityManager: EntityManager
) {
    @RuntimeType
    fun intercept(@SuperCall callable: Callable<*>, @Origin method: Method): Any? {
        println("TransactionInterceptor::intercept")
        var result: Any?
        var transactionStarted = false
        var transaction: EntityTransaction? = null
        if (method.isAnnotationPresent(Transactional::class.java)) {
            println("TransactionInterceptor::intercept Transactional")
            transaction = entityManager.transaction
            if (!transaction.isActive) {
                println("TransactionInterceptor::intercept Transactional::NotActive")
                transaction.begin()
                transactionStarted = true
            }
        }

        try {
            result = callable.call()
            if (transactionStarted) {
                transaction?.commit()
            }
        } catch (throwable: Throwable) {
            if (transactionStarted) {
                transaction?.rollback()
            }
            throw throwable
        }
        return result
    }
}