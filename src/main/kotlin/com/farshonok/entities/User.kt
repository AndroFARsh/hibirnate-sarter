package com.farshonok.entities

import com.querydsl.core.annotations.PropertyType
import com.querydsl.core.annotations.QueryType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.NamedAttributeNode
import jakarta.persistence.NamedEntityGraph
import jakarta.persistence.NamedSubgraph
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.Session
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.FetchProfile
import org.hibernate.graph.RootGraph

@NamedEntityGraph(
    name = "GraphWithCompanyAndChats",
    attributeNodes = [
        NamedAttributeNode("company"),
        NamedAttributeNode("userChats", subgraph = "chats"),
    ],
    subgraphs = [
        NamedSubgraph("chats", attributeNodes = [ NamedAttributeNode("chat") ])
    ]
)
@FetchProfile(
    name = "ProfileWithCompanyAndChats",
    fetchOverrides = [
        FetchProfile.FetchOverride(entity = User::class, association = "company"),
        FetchProfile.FetchOverride(entity = User::class, association = "userChats", mode = FetchMode.SELECT),
        FetchProfile.FetchOverride(entity = UserChat::class, association = "chat"),
    ],
)
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


fun Session.createGraphWithCompanyAndChats() : RootGraph<User> {
    val graph = createEntityGraph(User::class.java)
    graph.addAttributeNodes(User_.COMPANY, User_.USER_CHATS)

    val subgraph = graph.addSubgraph(User_.USER_CHATS, UserChat::class.java)
    subgraph.addAttributeNodes(UserChat_.CHAT)

    return graph;
}