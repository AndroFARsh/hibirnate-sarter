package com.farshonok.repositories

import java.io.Serializable
import java.util.Optional
import java.util.Properties

interface Repository<K: Serializable, E> {
    fun save(entity: E): E
    fun delete(id: K)
    fun update(entity: E)
    fun findById(id: K, properties: Map<String, String> = emptyMap()): Optional<E>
    fun findAll(): List<E>
}