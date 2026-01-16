package com.farshonok.repositories

import com.farshonok.entities.User
import jakarta.persistence.EntityManager
import org.hibernate.SessionFactory

class UserRepository(
    entityManager: EntityManager,
) : BaseRepository<Long, User>(entityManager, clazz = User::class.java)