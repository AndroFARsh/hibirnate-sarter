package com.farshonok.dao

import com.farshonok.entities.Payment
import com.farshonok.entities.User
import org.hibernate.Session

class DummyUserDao : UserDao {
    override fun findAll(session: Session): List<User> = emptyList()

    override fun findAllByFirstName(
        session: Session,
        firstName: String
    ): List<User> = emptyList()

    override fun findLimitedUsersOrderedByBirthday(
        session: Session,
        limit: Int
    ): List<User> = emptyList()

    override fun findAllByCompanyName(
        session: Session,
        companyName: String
    ): List<User> = emptyList()

    override fun findAllPaymentsByCompanyName(
        session: Session,
        companyName: String
    ): List<Payment> = emptyList()

    override fun findAveragePaymentAmountByFirstAndLastNames(
        session: Session,
        firstName: String,
        lastName: String
    ): Double = 0.0

    override fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session: Session): List<Array<Any>> = emptyList()

    override fun isItPossible(session: Session): List<Array<Any>> = emptyList()
}