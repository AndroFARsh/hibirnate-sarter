package com.farshonok.entities

import com.farshonok.listeners.RevisionListenerImpl
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp

@Entity
@RevisionEntity(RevisionListenerImpl::class)
data class Revision(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    val id: Long = 0,

    @RevisionTimestamp
    var timestamp: Long = 0,

    var userName: String? = null,
)