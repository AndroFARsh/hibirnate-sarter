package com.farshonok.entities

import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.envers.Audited

@Entity
@Table(name = "company", schema = "public")
//@BatchSize(size = 5)
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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