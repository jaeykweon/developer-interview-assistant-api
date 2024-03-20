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

    fun getType(): InterviewPracticeHistory.Type = InterviewPracticeHistory.Type.valueOf(typeValue.uppercase())

    fun getElapsedTime(): InterviewPracticeHistory.ElapsedTime = InterviewPracticeHistory.ElapsedTime(elapsedTimeValue)

    fun getFilePathOrNull(): InterviewPracticeHistory.FilePath? = filePathValue?.run { InterviewPracticeHistory.FilePath(this) }
}

data class InterviewPracticeHistoryResponse(
    val pkValue: Long,
    val question: InterviewQuestionResponse,
    val typeValue: String,
    val contentValue: String,
    val elapsedTimeValue: Int,
    val fileUrlValue: String?,
    val createdTimeValue: LocalDateTime,
) {
    companion object {
        @JvmStatic
        fun of(
            entity: InterviewPracticeHistoryEntity,
            interviewQuestionResponse: InterviewQuestionResponse,
        ): InterviewPracticeHistoryResponse {
            return InterviewPracticeHistoryResponse(
                pkValue = entity.pkValue,
                question = interviewQuestionResponse,
                typeValue = entity.typeValue,
                contentValue = entity.contentValue,
                elapsedTimeValue = entity.elapsedTimeValue,
                fileUrlValue = entity.filePathValue,
                createdTimeValue = entity.createdTime,
            )
        }
    }
}
