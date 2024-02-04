package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import java.time.LocalDateTime

data class RecordInterviewPracticeRequest(
    val interviewQuestionPkValue: Long,
    val typeValue: String,
    val elapsedTimeValue: Int,
    val filePathValue: String,
) {
    fun getInterviewQuestionPk(): InterviewQuestion.Pk = InterviewQuestion.Pk(interviewQuestionPkValue)

    fun getType(): InterviewPracticeHistory.Type = InterviewPracticeHistory.Type.valueOf(typeValue)

    fun getElapsedTime(): InterviewPracticeHistory.ElapsedTime = InterviewPracticeHistory.ElapsedTime(elapsedTimeValue)

    fun getFilePath(): InterviewPracticeHistory.FilePath = InterviewPracticeHistory.FilePath(filePathValue)
}

data class InterviewPracticeHistoryResponse(
    val pkValue: Long,
    val typeValue: String,
    val elapsedTimeValue: Int,
    val fileUrlValue: String,
    val createdTimeValue: LocalDateTime,
) {
    constructor(entity: InterviewPracticeHistoryEntity) : this(
        pkValue = entity.pkValue,
        typeValue = entity.typeValue,
        elapsedTimeValue = entity.elapsedTimeValue,
        fileUrlValue = entity.filePath,
        createdTimeValue = entity.getCreatedTime(),
    )
}
