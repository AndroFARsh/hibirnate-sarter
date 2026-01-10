package com.farshonok

import com.farshonok.entities.Payment
import com.farshonok.listeners.AuditTableListeners
import com.farshonok.listeners.AuditableListeners
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.fillDatabase
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.hibernate.LockMode
import org.hibernate.SessionFactory
import org.hibernate.event.service.spi.EventListenerRegistry
import org.hibernate.event.spi.EventType
import org.hibernate.internal.SessionFactoryImpl
import org.hibernate.jpa.AvailableHints
import org.hibernate.jpa.LegacySpecHints.HINT_JAVAEE_LOCK_TIMEOUT
import org.hibernate.jpa.QueryHints

fun main() {
    createSessionFactory().use { sessionFactory ->
        val listener = AuditTableListeners()

        val sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl::class.java)
        val listenerRegistry = sessionFactoryImpl.serviceRegistry.getService(EventListenerRegistry::class.java)

        listenerRegistry?.appendListeners(EventType.POST_INSERT, listener)
        listenerRegistry?.appendListeners(EventType.POST_UPDATE, listener)
        listenerRegistry?.appendListeners(EventType.POST_DELETE, listener)

        
        sessionFactory.fillDatabase()

        sessionFactory.openSession().use { session ->
            session.beginTransaction()

            val payment = session.find(Payment::class.java, 1)
            payment.amount += 10

            session.transaction.commit()
        }
    }
}