package com.farshonok.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true)
    val name: String,

    var usersInChat: Int = 0,
) {
    @OneToMany(mappedBy = "chat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var userChats: MutableList<UserChat> = mutableListOf()
}

