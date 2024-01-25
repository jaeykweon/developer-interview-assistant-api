package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import java.time.LocalDateTime

class InterviewPracticeHistoryRequest(
    val type: String,
    val elapsedTime: Long,
    val filePath: String,
)

data class InterviewPracticeHistoryResponse(
    val pk: Long,
    val type: String,
    val elapsedTime: Int,
    val filePath: String,
    val createdTime: LocalDateTime,
) {
    constructor(entity: InterviewPracticeHistoryEntity) : this(
        pk = entity.pkValue,
        type = entity.typeValue,
        elapsedTime = entity.elapsedTimeValue,
        filePath = entity.filePath,
        createdTime = entity.getCreatedTime(),
    )
}
