package com.farshonok.entities

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable

enum class Operation {
    Update, Delete, Insert
}

@Entity
data class Audit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var entityId: String?,
    var entityContent: String?,
    @Enumerated(EnumType.STRING)
    var operation: Operation,
)