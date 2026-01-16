package com.farshonok.dto.mappers

interface Mapper<F, T> {
    fun map(from: F): T
}