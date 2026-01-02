package com.farshonok.convertes

import com.farshonok.entities.Birthday
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.sql.Date

@Converter(autoApply = true)
class BirthdayConverter : AttributeConverter<Birthday, Date> {
    override fun convertToDatabaseColumn(attribute: Birthday?): Date? =
        attribute?.date.let(Date::valueOf)

    override fun convertToEntityAttribute(dbData: Date?): Birthday? =
        dbData?.toLocalDate()?.let { Birthday(it) }
}