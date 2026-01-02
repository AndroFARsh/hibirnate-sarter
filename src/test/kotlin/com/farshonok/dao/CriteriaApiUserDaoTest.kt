package com.farshonok.dao

import com.farshonok.com.farshonok.utils.fillTestData
import com.farshonok.com.farshonok.utils.createTestSessionFactory
import com.farshonok.entities.Payment
import com.farshonok.entities.User
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.SessionFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class CriteriaApiUserDaoTest {
    private var sessionFactory: SessionFactory = createTestSessionFactory()
    private lateinit var userDao: UserDao

    @BeforeEach
    fun setup() {
        sessionFactory.fillTestData()
        userDao = CriteriaApiUserDao()
    }

    @AfterEach
    fun tearDown() {
        sessionFactory.close()
    }

    @Test
    fun findAll() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val results = userDao.findAll(session)
        assertThat(results).hasSize(5)

        val fullNames = results.map(User::fullName).toList()
        assertThat(fullNames).containsExactlyInAnyOrder(
            "Bill Gates",
            "Steve Jobs",
            "Sergey Brin",
            "Tim Cook",
            "Diane Greene"
        )

        transaction.commit()
    }

    @Test
    fun findAllByFirstName() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val results = userDao.findAllByFirstName(session, "Bill")

        assertThat(results).hasSize(1)
        assertThat(results[0].fullName).isEqualTo("Bill Gates")

        transaction.commit()
    }

    @Test
    fun findLimitedUsersOrderedByBirthday() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val limit = 3
        val results = userDao.findLimitedUsersOrderedByBirthday(session, limit)
        assertThat(results).hasSize(limit)

        val fullNames = results.stream().map(User::fullName).toList()
        assertThat(fullNames).contains("Diane Greene", "Steve Jobs", "Bill Gates")

        transaction.commit()
    }

    @Test
    fun findAllByCompanyName() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val results = userDao.findAllByCompanyName(session, "Google")
        assertThat(results).hasSize(2)

        val fullNames = results.stream().map(User::fullName).toList()
        assertThat(fullNames).containsExactlyInAnyOrder("Sergey Brin", "Diane Greene")

        transaction.commit()
    }

    @Test
    fun findAllPaymentsByCompanyName() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val applePayments = userDao.findAllPaymentsByCompanyName(session, "Apple")
        assertThat(applePayments).hasSize(5)

        val amounts = applePayments.stream().map<Any?>(Payment::amount).toList()
        assertThat(amounts).contains(250, 500, 600, 300, 400)

        transaction.commit()
    }

    @Test
    fun findAveragePaymentAmountByFirstAndLastNames() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val averagePaymentAmount: Double =
            userDao.findAveragePaymentAmountByFirstAndLastNames(session, "Bill", "Gates")
        assertThat(averagePaymentAmount).isEqualTo(300.0)

        transaction.commit()
    }

    @Test
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val results =
            userDao.findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session)
        assertThat(results).hasSize(3)

        val orgNames = results.map { a -> a[0] as? String }.toList()
        assertThat(orgNames).contains("Apple", "Google", "Microsoft")

        val orgAvgPayments = results.map { a -> a[1] as? Double }.toList()
        assertThat(orgAvgPayments).contains(410.0, 400.0, 300.0)

        transaction.commit()
    }

    @Test
    fun isItPossible() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val results  = userDao.isItPossible(session)
        assertThat(results).hasSize(2)

        val names = results.map { r -> (r[0] as? User)?.fullName }.toList()
        assertThat(names).contains("Sergey Brin", "Steve Jobs")

        val averagePayments = results.map { r -> r[1] as? Double }.toList()
        assertThat(averagePayments).contains(500.0, 450.0)

        transaction.commit()
    }

    @Test
    fun findAveragePaymentByFilter() = sessionFactory.openSession().use { session ->
        val transaction = session.beginTransaction()

        val averagePaymentAmount1 =
            userDao.findAveragePaymentByFilter(session, PaymentFilter(firstName = "Bill"))
        assertThat(averagePaymentAmount1).isEqualTo(300.0)

        val averagePaymentAmount2 =
            userDao.findAveragePaymentByFilter(session, PaymentFilter(lastName = "Gates"))
        assertThat(averagePaymentAmount2).isEqualTo(300.0)

        val averagePaymentAmount3 =
            userDao.findAveragePaymentByFilter(session, PaymentFilter(firstName = "Bill", lastName = "Gates"))
        assertThat(averagePaymentAmount3).isEqualTo(300.0)

        val averagePaymentAmount4 =
            userDao.findAveragePaymentByFilter(session, PaymentFilter())
        assertThat(averagePaymentAmount4).isEqualTo(382.14285714285717)

        transaction.commit()
    }
}