package com.farshonok.dto

import java.time.LocalDate

data class UserCreateDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDay: LocalDate,
    val companyId: Long
)

