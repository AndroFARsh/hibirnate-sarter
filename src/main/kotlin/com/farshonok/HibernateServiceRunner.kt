package com.farshonok

import com.farshonok.dto.UserCreateDto
import com.farshonok.dto.mappers.CompanyReadMapper
import com.farshonok.dto.mappers.UserCreateMapper
import com.farshonok.dto.mappers.UserReanMapper
import com.farshonok.interceptor.TransactionInterceptor
import com.farshonok.repositories.CompanyRepository
import com.farshonok.repositories.UserRepository
import com.farshonok.services.UserService
import com.farshonok.utils.createSessionFactory
import com.farshonok.utils.proxyCurrentSession
import jakarta.validation.Validation
import jakarta.validation.Validator
import net.bytebuddy.ByteBuddy
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.matcher.ElementMatchers
import java.time.LocalDate
import java.time.Month

fun main() {
    createSessionFactory().use { sessionFactory ->
        //sessionFactory.fillDatabase()
        val validatorFactory = Validation.buildDefaultValidatorFactory()
        val validator: Validator = validatorFactory.validator

        val session = sessionFactory.proxyCurrentSession()
        val transactionInterceptor = TransactionInterceptor(session)

        val companyRepository = CompanyRepository(session)
        val userRepository = UserRepository(session)

        val companyReadMapper = CompanyReadMapper()
        val userReadMapper = UserReanMapper(companyReadMapper)
        val userCreateMapper = UserCreateMapper(companyRepository)

//        val userService = UserService(userRepository, userReadMapper, userCreateMapper)
        // service should be open class and methods open too for bytebuddy to work
        val userService = ByteBuddy()
            .subclass(UserService::class.java)
            .method(ElementMatchers.any())
            .intercept(MethodDelegation.to(transactionInterceptor))
            .make()
            .load(UserService::class.java.classLoader)
            .loaded
            .declaredConstructors[0]
            .newInstance(userRepository, validator, userReadMapper, userCreateMapper) as UserService

        val userId = userService.create(
            UserCreateDto(
                email = "a11_gmail.com",
                firstName = "Anton11",
                lastName = "K11",
                birthDay = LocalDate.of(1985, Month.APRIL, 1),
                companyId = 1L
            )
        )

        userService.findById(userId).ifPresent {
            println(it)
        }

        println(userService.findById(100L).isEmpty)
    }
}