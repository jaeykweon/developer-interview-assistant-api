package org.idd.dia.domain

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Convert
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@JvmInline
value class CreatedAt(
    val value: LocalDateTime = LocalDateTime.now()
)

@JvmInline
value class UpdatedAt(
    val value: LocalDateTime = LocalDateTime.now()
)

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class HistoryEntity(
    @Convert(converter = DbTimeConverter::class)
    val createdAt: CreatedAt = CreatedAt(),

    @Convert(converter = DbTimeConverter::class)
    var updatedAt: UpdatedAt = UpdatedAt()
)
