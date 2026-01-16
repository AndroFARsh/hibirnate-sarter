package com.farshonok.dto.mappers

import com.farshonok.dto.CompanyReadDto
import com.farshonok.dto.UserReadDto
import com.farshonok.entities.Company
import com.farshonok.entities.User

class UserReanMapper(
    val companyReadMapper: Mapper<Company, CompanyReadDto>
) : Mapper<User, UserReadDto> {
    override fun map(from: User) =
        UserReadDto(
            from.id,
            from.email,
            from.fullName,
            from.firstName,
            from.lastName,
            from.birthDate,
            companyReadMapper.map(from.company),
        )
}

