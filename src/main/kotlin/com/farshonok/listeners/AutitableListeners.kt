package com.farshonok.listeners

import com.farshonok.entities.AuditableEntity
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.Instant

class AuditableListeners {

    @PrePersist
    fun prePersist(entity: AuditableEntity) {
        println("AuditableListeners::prePersist")
        entity.createdAt = Instant.now()
        // createdBy = SecurityContext.getCurrentUser()
    }

    @PreUpdate
    fun preUpdate(entity: AuditableEntity) {
        println("AuditableListeners::preUpdate")
        entity.updatedAt = Instant.now()
        // createdBy = SecurityContext.getCurrentUser()
    }
}