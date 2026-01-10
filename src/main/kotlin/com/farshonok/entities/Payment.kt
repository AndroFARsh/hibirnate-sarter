package com.farshonok.entities

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OptimisticLockType
import org.hibernate.annotations.OptimisticLocking
import java.time.Instant

@Entity
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var amount: Int,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    var receiver: User,
) : AuditableEntity()
{
//    @Version
//    private val version: Long = 0

    @PrePersist
    fun prePersist() {
        println("Payment::prePersist")
        createdAt = Instant.now()
        // createdBy = SecurityContext.getCurrentUser()
    }

    @PreUpdate
    fun preUpdate() {
        println("Payment::preUpdate")
        updatedAt = Instant.now()
        // createdBy = SecurityContext.getCurrentUser()
    }
}

