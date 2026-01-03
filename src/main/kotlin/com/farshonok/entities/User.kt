package com.farshonok.entities

import com.farshonok.convertes.BirthdayConverter
import com.querydsl.core.annotations.PropertyType
import com.querydsl.core.annotations.QueryType
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company,
) {
    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    val userChats: MutableList<UserChat> = mutableListOf()

    @OneToMany(mappedBy = "receiver")
    @Fetch(FetchMode.SUBSELECT)
    val payments: MutableList<Payment> = mutableListOf()
}