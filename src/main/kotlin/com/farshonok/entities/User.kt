package com.farshonok.entities

import com.farshonok.convertes.BirthdayConverter
import com.querydsl.core.annotations.PropertyType
import com.querydsl.core.annotations.QueryType
import jakarta.persistence.*

@Entity
@Table(name = "users", schema = "public")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var email: String,
    var fullName: String,
    var firstName: String,
    var lastName: String,

    @get:QueryType(PropertyType.DATE)
    var birthDate: Birthday,

    @ManyToOne
    @JoinColumn(name = "company_id")
    var company: Company,
) {
    @OneToMany(mappedBy = "user")
    val userChats: MutableList<UserChat> = mutableListOf()

    @OneToMany(mappedBy = "receiver")
    val payments: MutableList<Payment> = mutableListOf()
}