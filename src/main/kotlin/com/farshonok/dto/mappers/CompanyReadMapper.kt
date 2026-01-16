package com.farshonok.dto.mappers

import com.farshonok.dto.CompanyReadDto
import com.farshonok.entities.Company

class CompanyReadMapper : Mapper<Company, CompanyReadDto> {
    override fun map(from: Company) = CompanyReadDto(from.id, from.name)
}