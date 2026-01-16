package com.farshonok.repositories

import com.farshonok.entities.Payment
import jakarta.persistence.EntityManager
import org.hibernate.SessionFactory

class PaymentRepository(
    entityManager: EntityManager
) : BaseRepository<Long, Payment>(entityManager, clazz = Payment::class.java)

