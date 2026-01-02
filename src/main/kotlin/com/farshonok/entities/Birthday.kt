package com.farshonok.entities

import jakarta.persistence.Embeddable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Embeddable
data class Birthday(
    val date: LocalDate,
) {
    val age: Int
        get() = ChronoUnit.YEARS.between(date, LocalDate.now()).toInt()
}