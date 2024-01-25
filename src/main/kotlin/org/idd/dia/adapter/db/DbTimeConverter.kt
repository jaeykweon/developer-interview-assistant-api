package org.idd.dia.adapter.db

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.sql.Timestamp
import java.time.LocalDateTime

@Converter(autoApply = true)
object DbTimeConverter : AttributeConverter<LocalDateTime, Timestamp> {
    override fun convertToDatabaseColumn(attribute: LocalDateTime?): Timestamp? = attribute?.run { Timestamp.valueOf(this) }

    override fun convertToEntityAttribute(dbData: Timestamp?): LocalDateTime? = dbData?.toLocalDateTime()
}
