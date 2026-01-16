package com.farshonok.dto

import com.farshonok.entities.Birthday

data class UserReadDto(
    val id: Long,
    val email: String,
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val birthDate: Birthday,
    val company: CompanyReadDto
)

