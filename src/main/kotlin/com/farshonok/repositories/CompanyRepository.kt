package com.farshonok.repositories

import com.farshonok.entities.Company
import com.farshonok.entities.User
import jakarta.persistence.EntityManager
import org.hibernate.SessionFactory

class CompanyRepository(
    entityManager: EntityManager,
) : BaseRepository<Long, Company>(entityManager, clazz = Company::class.java)