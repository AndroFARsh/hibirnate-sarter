package com.farshonok.repositories

import com.farshonok.entities.Payment
import jakarta.persistence.EntityManager
import java.io.Serializable
import java.util.*

abstract class BaseRepository<K : Serializable, E>(
    private val entityManager: EntityManager,
    private val clazz: Class<E>,
) : Repository<K, E> {

    override fun save(entity: E): E {
        entityManager.persist(entity)
        return entity
    }

    override fun delete(id: K) {
        val entity = entityManager.find(Payment::class.java, id)
        if (entity != null) {
            entityManager.remove(entity)
        }
    }

    override fun update(entity: E) {
        entityManager.merge(entity)
    }

    @Suppress("UNCHECKED_CAST")
    override fun findById(id: K, properties: Map<String, String>): Optional<E> =
        Optional.ofNullable(entityManager.find(clazz, id, properties)) as Optional<E>

    override fun findAll(): List<E> {
        val criteria = entityManager.criteriaBuilder.createQuery(clazz)
        criteria.from(clazz)
        return entityManager.createQuery(criteria)
            .resultList
            .filterNotNull()
    }
}