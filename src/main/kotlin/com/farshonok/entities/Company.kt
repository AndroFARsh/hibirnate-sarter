package com.farshonok.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.BatchSize

@Entity
@Table(name = "company", schema = "public")
//@BatchSize(size = 5)
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    var name: String,
) {

    // extract from constructor to perevent toString, equal, hashCOde exception
    @OneToMany(mappedBy = "company")
    var users: MutableList<User> = mutableListOf()
}