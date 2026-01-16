package com.farshonok.dto.mappers

import com.farshonok.dto.UserCreateDto
import com.farshonok.entities.Birthday
import com.farshonok.entities.User
import com.farshonok.repositories.CompanyRepository

class UserCreateMapper(
    val companyRepository: CompanyRepository
) : Mapper<UserCreateDto, User> {
    override fun map(from: UserCreateDto) =
        User(
            id = 0,
            from.email,
            "${from.firstName} ${from.lastName}",
            from.firstName,
            from.lastName,
            Birthday(from.birthDay),
            companyRepository.findById(from.companyId).orElseThrow(),
        )
}

