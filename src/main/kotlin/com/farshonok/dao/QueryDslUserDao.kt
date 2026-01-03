package com.farshonok.dao

import com.farshonok.entities.*
import com.farshonok.entities.QCompany.Companion.company
import com.farshonok.entities.QPayment.Companion.payment
import com.farshonok.entities.QUser.Companion.user
import com.querydsl.core.Tuple
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQuery
import org.hibernate.Session

class QueryDslUserDao : UserDao {
    override fun findAll(session: Session): List<User> = JPAQuery<User>(session)
        .select(user)
        .from(company)
        .join(company.users, user)
        .fetch()

    override fun findAllByFirstName(
        session: Session,
        firstName: String
    ): List<User> = JPAQuery<User>(session)
        .select(user)
        .from(user)
        .where(user.firstName.eq(firstName))
        .fetch()

    override fun findLimitedUsersOrderedByBirthday(
        session: Session,
        limit: Int
    ): List<User> = JPAQuery<User>(session)
        .select(user)
        .from(user)
        .orderBy(user.birthDate.date.asc())
        .limit(limit.toLong())
        .fetch()

    override fun findAllByCompanyName(
        session: Session,
        companyName: String
    ): List<User> = JPAQuery<User>(session)
        .select(user)
        .from(company)
        .innerJoin( company.users, user)
        .where(company.name.eq(companyName))
        .fetch()

    override fun findAllPaymentsByCompanyName(
        session: Session,
        companyName: String
    ): List<Payment> {
        return JPAQuery<Payment>(session)
            .select(payment)
            .from(company)
            .innerJoin( company.users, user)
            .innerJoin(user.payments, payment)
            .where(company.name.eq(companyName))
            .orderBy(user.firstName.asc(), payment.amount.asc())
            .fetch()
    }

    override fun findAveragePaymentAmountByFirstAndLastNames(
        session: Session,
        firstName: String,
        lastName: String
    ): Double = JPAQuery<Double>(session)
        .select(payment.amount.avg())
        .from(user)
        .innerJoin(user.payments, payment)
        .where(
            user.firstName.eq(firstName),
            user.lastName.eq(lastName)
        )
        .fetchOne() ?: 0.0

    override fun findAveragePaymentByFilter(
        session: Session,
        filter: PaymentFilter
    ): Double {
        val predicates = mutableListOf<Predicate>()
        filter.firstName?.let { predicates.add(user.firstName.eq(it)) }
        filter.lastName?.let { predicates.add(user.lastName.eq(it)) }

        return JPAQuery<Double>(session)
            .select(payment.amount.avg())
            .from(user)
            .innerJoin(user.payments, payment)
            .where(*predicates.toTypedArray())
            .fetchOne() ?: 0.0
    }

    override fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session: Session): List<Array<Any>> = JPAQuery<Tuple>(session)
        .select(company.name, payment.amount.avg())
        .from(company)
        .innerJoin(company.users, user)
        .innerJoin(user.payments, payment)
        .groupBy(company.name)
        .orderBy(company.name.asc())
        .fetch()
        .map { it.toArray() }

    override fun isItPossible(session: Session): List<Array<Any>> {
        return JPAQuery<Tuple>(session)
            .select(user, payment.amount.avg())
            .from(user)
            .join(user.payments, payment)
            .groupBy(user)
            .having(payment.amount.avg().gt(
                JPAQuery<Double>(session)
                    .select(payment.amount.avg())
                    .from(payment)
                )
            )
            .orderBy(user.firstName.asc()   )
            .fetch()
            .map { it.toArray() }
    }
}