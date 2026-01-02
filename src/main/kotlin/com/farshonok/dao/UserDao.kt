package com.farshonok.dao

import com.farshonok.entities.Payment
import com.farshonok.entities.User
import org.hibernate.Session
import java.util.Objects


interface UserDao {
    /**
     * Возвращает всех сотрудников
     */
    fun findAll(session: Session): List<User>

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    fun findAllByFirstName(session: Session, firstName: String): List<User>

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    fun findLimitedUsersOrderedByBirthday(session: Session, limit: Int): List<User>

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    fun findAllByCompanyName(session: Session, companyName: String): List<User>

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    fun findAllPaymentsByCompanyName(session: Session, companyName: String): List<Payment>

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    fun findAveragePaymentAmountByFirstAndLastNames(session: Session, firstName: String, lastName: String): Double

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    fun findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session: Session):  List<Array<Any>>

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    fun isItPossible(session: Session): List<Array<Any>>

}