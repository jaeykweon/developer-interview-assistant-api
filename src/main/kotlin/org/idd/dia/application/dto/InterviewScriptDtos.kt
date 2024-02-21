package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import java.time.LocalDateTime

data class InterviewScriptCreateRequest(
    val questionPkValue: Long,
    val contentValue: String,
) {
    fun getQuestionPk(): InterviewQuestion.Pk = InterviewQuestion.Pk(questionPkValue)

    fun getContent(): InterviewScript.Content = InterviewScript.Content(contentValue)
}

data class InterviewScriptResponse(
    val pkValue: Long,
    val ownerPkValue: Long,
    val question: InterviewQuestionResponse,
    val contentValue: String,
    val createdTimeValue: LocalDateTime,
    val lastModifiedTimeValue: LocalDateTime,
    val lastReadTimeValue: LocalDateTime,
) {
    companion object {
        @JvmStatic
        fun from(entity: InterviewScriptEntity): InterviewScriptResponse {
            val question = InterviewQuestionResponse.withoutCheckingBookmark(entity.question)
            return InterviewScriptResponse(
                pkValue = entity.getPk().value,
                ownerPkValue = entity.owner.pkValue,
                question = question,
                contentValue = entity.contentValue,
                createdTimeValue = entity.createdTime,
                lastModifiedTimeValue = entity.lastModifiedTime,
                lastReadTimeValue = entity.lastReadTime,
            )
        }
    }
}

data class InterviewScriptUpdateRequest(
    val contentValue: String,
) {
    fun getContent() = InterviewScript.Content(contentValue)
}
