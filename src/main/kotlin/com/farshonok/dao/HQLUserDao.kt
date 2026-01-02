package com.farshonok.dao

import com.farshonok.entities.Payment
import com.farshonok.entities.User
import org.hibernate.Session

class HQLUserDao : UserDao {
    override fun findAll(session: Session): List<User> =
        session.createQuery("select u from User u", User::class.java)
            .list()

    override fun findAllByFirstName(
        session: Session,
        firstName: String
    ): List<User> =
        session.createQuery(
            """
           select u from User u
           where u.firstName = :firstName
        """, User::class.java
        )
            .apply { setParameter("firstName", firstName) }
            .list()

    override fun findLimitedUsersOrderedByBirthday(
        session: Session,
        limit: Int
    ): List<User> = session.createQuery(
        """
       select u from User u
       order by u.birthDate 
    """, User::class.java
    )
        .apply { maxResults = limit }
        .list()

    override fun findAllByCompanyName(
        session: Session,
        companyName: String
    ): List<User> = session.createQuery(
        """
       select u from User u
       join u.company c
       where c.name = :companyName
    """, User::class.java
    )
        .apply { setParameter("companyName", companyName) }
        .list()

    override fun findAllPaymentsByCompanyName(
        session: Session,
        companyName: String
    ): List<Payment> = session.createQuery(
        """
       select p from Payment p
       join p.receiver u
       join u.company c
       where c.name = :companyName
       order by u.firstName, p.amount
    """, Payment::class.java
    )
        .apply { setParameter("companyName", companyName) }
        .list()

    override fun findAveragePaymentAmountByFirstAndLastNames(
        session: Session,
        firstName: String,
        lastName: String
    ): Double = session.createQuery(
        """
       select avg(p.amount) from Payment p
       join p.receiver u
       where u.firstName = :firstName and u.lastName = :lastName
    """, Double::class.java
    )
        .apply {
            setParameter("firstName", firstName)
            setParameter("lastName", lastName)
        }
        .uniqueResult()

    override fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session: Session): List<Array<Any>> =
        session.createQuery(
            """
           select c.name, avg(p.amount) from Company c
           join c.users u
           join u.payments p
           group by c.name
           order by c.name
        """, Array<Any>::class.java
        )
            .list()

    override fun isItPossible(session: Session): List<Array<Any>> =
        session.createQuery(
            """
            select u, avg(p.amount) from User u
            join u.payments p
            group by u.id           
            having avg(p.amount) > (select avg(p.amount) from Payment p)
            order by u.firstName
        """, Array<Any>::class.java
        )
            .list()
}