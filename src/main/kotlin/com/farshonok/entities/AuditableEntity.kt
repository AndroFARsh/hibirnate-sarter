package com.farshonok.entities

import com.farshonok.listeners.AuditableListeners
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.envers.Audited
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditableListeners::class)
@Audited
abstract class AuditableEntity {
    var createdAt: Instant? = null
    var createdBy: String? = null

    var updatedAt: Instant? = null
    var updatedBy: String?= null
}