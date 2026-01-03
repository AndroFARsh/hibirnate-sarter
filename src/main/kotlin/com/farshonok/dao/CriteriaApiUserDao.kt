package com.farshonok.dao

import com.farshonok.entities.*
import jakarta.persistence.criteria.Predicate
import org.hibernate.Session

class CriteriaApiUserDao : UserDao {
    override fun findAll(session: Session): List<User> {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(User::class.java)

        val user = criteria.from(User::class.java)
        criteria.select(user)
        user.fetch(User_.company)

        return session
            .createQuery(criteria)
            .list()
    }

    override fun findAllByFirstName(
        session: Session,
        firstName: String
    ): List<User> {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(User::class.java)

        val user = criteria.from(User::class.java)
        criteria
            .select(user)
            .where(cb.equal(user.get<String>(User_.firstName), firstName))

        return session
            .createQuery(criteria)
            .list()
    }

    override fun findLimitedUsersOrderedByBirthday(
        session: Session,
        limit: Int
    ): List<User> {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(User::class.java)

        val user = criteria.from(User::class.java)
        criteria
            .select(user)
            .orderBy(cb.asc(user.get(User_.birthDate).get(Birthday_.date)))

        return session
            .createQuery(criteria)
            .setMaxResults(limit)
            .list()
    }

    override fun findAllByCompanyName(
        session: Session,
        companyName: String
    ): List<User>
    {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(User::class.java)

        val company = criteria.from(Company::class.java)
        val users = company.join(Company_.users)

        criteria
            .select(users)
            .where(cb.equal(company.get(Company_.name), companyName))

        return session
            .createQuery(criteria)
            .list()
    }
    override fun findAllPaymentsByCompanyName(
        session: Session,
        companyName: String
    ): List<Payment> {
        val cb = session.criteriaBuilder

        val criteria = cb.createQuery(Payment::class.java)

        val payment = criteria.from(Payment::class.java)
        val user = payment.join(Payment_.receiver)
        val company = user.join(User_.company)

        criteria
            .select(payment)
            .where(cb.equal(company.get(Company_.name), companyName))
            .orderBy(
                cb.asc(user.get(User_.firstName)),
                cb.asc(payment.get(Payment_.amount))
            )

        return session
            .createQuery(criteria)
            .list()
    }

    override fun findAveragePaymentAmountByFirstAndLastNames(
        session: Session,
        firstName: String,
        lastName: String
    ): Double {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(Double::class.java)

        val payment = criteria.from(Payment::class.java)
        val user = payment.join(Payment_.receiver)

        criteria
            .select(cb.avg(payment.get(Payment_.amount)))
            .where(cb.and(
                cb.equal(user.get(User_.firstName), firstName),
                cb.equal(user.get(User_.lastName), lastName),
            ))

        return session
            .createQuery(criteria)
            .uniqueResult()
    }

    override fun findAveragePaymentByFilter(
        session: Session,
        filter: PaymentFilter
    ): Double {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(Double::class.java)

        val payment = criteria.from(Payment::class.java)
        val user = payment.join(Payment_.receiver)

        val predicates = mutableListOf<Predicate>()
        filter.firstName?.let { predicates.add(cb.equal(user.get(User_.firstName), it)) }
        filter.lastName?.let { predicates.add(cb.equal(user.get(User_.lastName), it)) }

        criteria
            .select(cb.avg(payment.get(Payment_.amount)))
            .where(predicates)

        return session
            .createQuery(criteria)
            .uniqueResult()
    }

    override fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session: Session): List<Array<Any>> {
        val cb = session.criteriaBuilder
        val criteria = cb.createQuery(Array<Any>::class.java)

        val company = criteria.from(Company::class.java)
        val users = company.join(Company_.users)
        val payments = users.join(User_.payments)

        criteria.select(
            cb.array(
                company.get(Company_.name),
                cb.avg(payments.get(Payment_.amount))
            )
        )
            .groupBy(company.get(Company_.name))
            .orderBy(cb.asc(company.get(Company_.name)))

        return session
            .createQuery(criteria)
            .list()
    }

    override fun isItPossible(session: Session): List<Array<Any>> {
        val cb = session.criteriaBuilder

        val criteria = cb.createQuery(Array<Any>::class.java)
        val user = criteria.from(User::class.java)
        val payments = user.join(User_.payments)

        val subquery = criteria.subquery(Double::class.java)
        val paymentSubquery = subquery.from(Payment::class.java)

        criteria.select(
            cb.array(
                user, cb.avg(payments.get(Payment_.amount)),
            )
        )
            .groupBy(user)
            .having(cb.greaterThan(
                cb.avg(payments.get(Payment_.amount)),
                subquery.select(cb.avg(paymentSubquery.get(Payment_.amount)))))
            .orderBy(cb.asc(user.get(User_.firstName)))

        return session
            .createQuery(criteria)
            .list()
    }
}