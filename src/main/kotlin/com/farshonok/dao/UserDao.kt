package com.farshonok.dao

import com.farshonok.entities.Payment
import com.farshonok.entities.User
import org.hibernate.Session


interface UserDao {
    /**
     * Returns all employees
     */
    fun findAll(session: Session): List<User>

    /**
     * Returns all employees with the specified first name
     */
    fun findAllByFirstName(session: Session, firstName: String): List<User>

    /**
     * Returns the first {limit} employees ordered by date of birth (ascending order)
     */
    fun findLimitedUsersOrderedByBirthday(session: Session, limit: Int): List<User>

    /**
     * Returns all employees of the company with the specified name
     */
    fun findAllByCompanyName(session: Session, companyName: String): List<User>

    /**
     * Returns all payments received by employees of the company with the specified name,
     * ordered by employee first name and then by payment amount
     */
    fun findAllPaymentsByCompanyName(session: Session, companyName: String): List<Payment>

    /**
     * Returns the average salary of the employee with the specified first and last name
     */
    fun findAveragePaymentAmountByFirstAndLastNames(session: Session, firstName: String, lastName: String): Double

    /**
     * Returns the average salary with the specified filter
     */
    fun findAveragePaymentByFilter(session: Session, filter: PaymentFilter): Double

    /**
     * Returns, for each company: the company name and the average salary of all its employees.
     * Companies are ordered by company name.
     */
    fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session: Session):  List<Array<Any>>

    /**
     * Returns a list containing: employee (User object) and average payment amount,
     * but only for employees whose average payment amount is greater than
     * the overall average payment amount of all employees.
     * Ordered by employee first name.
     */
    fun isItPossible(session: Session): List<Array<Any>>

}