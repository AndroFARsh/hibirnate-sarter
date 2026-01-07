package com.farshonok.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Version
import org.hibernate.LockMode
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OptimisticLock
import org.hibernate.annotations.OptimisticLockType
import org.hibernate.annotations.OptimisticLocking
import javax.lang.model.SourceVersion

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
)
//{
//    @Version
//    private val version: Long = 0
//}