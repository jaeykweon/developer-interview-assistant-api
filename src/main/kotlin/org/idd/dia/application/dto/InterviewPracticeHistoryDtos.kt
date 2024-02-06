package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import java.time.LocalDateTime

data class RecordInterviewPracticeRequest(
    val interviewQuestionPkValue: Long,
    val contentValue: String,
    val typeValue: String,
    val elapsedTimeValue: Int,
    val filePathValue: String? = null,
) {
    fun getInterviewQuestionPk(): InterviewQuestion.Pk = InterviewQuestion.Pk(interviewQuestionPkValue)

    fun getContent(): InterviewPracticeHistory.Content = InterviewPracticeHistory.Content(contentValue)

    fun getType(): InterviewPracticeHistory.Type = InterviewPracticeHistory.Type.valueOf(typeValue)

    fun getElapsedTime(): InterviewPracticeHistory.ElapsedTime = InterviewPracticeHistory.ElapsedTime(elapsedTimeValue)

    fun getFilePathOrNull(): InterviewPracticeHistory.FilePath? = filePathValue?.run { InterviewPracticeHistory.FilePath(this) }
}

data class InterviewPracticeHistoryResponse(
    val pkValue: Long,
    val typeValue: String,
    val contentValue: String,
    val elapsedTimeValue: Int,
    val fileUrlValue: String?,
    val createdTimeValue: LocalDateTime,
) {
    constructor(entity: InterviewPracticeHistoryEntity) : this(
        pkValue = entity.pkValue,
        typeValue = entity.typeValue,
        contentValue = entity.content,
        elapsedTimeValue = entity.elapsedTimeValue,
        fileUrlValue = entity.filePath,
        createdTimeValue = entity.getCreatedTime(),
    )
}
