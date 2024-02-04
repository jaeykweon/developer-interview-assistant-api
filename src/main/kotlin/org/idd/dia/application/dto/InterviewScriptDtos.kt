package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import java.time.LocalDateTime

data class InterviewScriptCreateRequest(
    val questionPkValue: Long,
    val content: InterviewScript.Content,
) {
    fun getQuestionPk(): InterviewQuestion.Pk = InterviewQuestion.Pk(questionPkValue)
}

data class InterviewScriptResponse(
    val pk: Long,
    val ownerPk: Long,
    val question: InterviewQuestionResponse,
    val content: String,
    val createdTime: LocalDateTime,
    val lastModifiedTime: LocalDateTime,
    val lastReadTime: LocalDateTime,
) {
    companion object {
        @JvmStatic
        fun from(entity: InterviewScriptEntity): InterviewScriptResponse {
            val question = InterviewQuestionResponse.from(entity.question)
            return InterviewScriptResponse(
                pk = entity.getPk().value,
                ownerPk = entity.owner.pkValue,
                question = question,
                content = entity.getContent().value,
                createdTime = entity.getCreatedTime(),
                lastModifiedTime = entity.getLastModifiedTime(),
                lastReadTime = entity.getLastReadTime(),
            )
        }
    }
}

data class InterviewScriptUpdateRequest(
    val questionPk: InterviewQuestion.Pk,
    val content: InterviewScript.Content,
)
