package com.farshonok.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Positive
import java.time.LocalDate


data class UserCreateDto(
    @get:NotBlank
    @get:Email
    val email: String,
    @get:NotBlank
    val firstName: String,
    @get:NotBlank
    val lastName: String,
    val birthDay: LocalDate,
    @get:Positive
    val companyId: Long
)

