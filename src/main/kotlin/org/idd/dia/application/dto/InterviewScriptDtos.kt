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

// todo: 질문&스크립트를 세트를 이걸로 변경

/**
 * Script는 스크립트 그 자체를 의미
 * ScriptForm은 질문과 스크립트를 같이 의미
 */
data class InterviewScriptFormResponse(
    val question: InterviewQuestionResponse,
    val script: InterviewScriptResponseV2?,
) {
    companion object {
        @JvmStatic
        fun ofGuest(questionResponse: InterviewQuestionResponse): InterviewScriptFormResponse {
            return InterviewScriptFormResponse(
                question = questionResponse,
                script = null,
            )
        }

        @JvmStatic
        fun ofMember(
            questionResponse: InterviewQuestionResponse,
            scriptResponse: InterviewScriptResponseV2?,
        ): InterviewScriptFormResponse {
            return InterviewScriptFormResponse(
                question = questionResponse,
                script = scriptResponse,
            )
        }

        @JvmStatic
        fun multiOfGuest(questionResponses: Collection<InterviewQuestionResponse>): List<InterviewScriptFormResponse> {
            return questionResponses.map { questionResponse ->
                return@map ofGuest(questionResponse)
            }
        }

        @JvmStatic
        fun multiOfMember(
            questionResponses: Collection<InterviewQuestionResponse>,
            scriptResponses: Collection<InterviewScriptResponseV2>,
        ): List<InterviewScriptFormResponse> {
            return questionResponses.map { questionResponse ->
                val targetScript: InterviewScriptResponseV2? =
                    scriptResponses.find { scriptResponse ->
                        scriptResponse.questionPk == questionResponse.pkValue
                    }
                return@map ofMember(questionResponse, targetScript)
            }
        }
    }
}

// todo: InterviewScriptResponse 를 이거로 변경
data class InterviewScriptResponseV2(
    val pk: Long,
    val owner: Long,
    val questionPk: Long,
    val content: String,
    val createdTime: LocalDateTime,
    val lastModifiedTime: LocalDateTime,
    val lastReadTime: LocalDateTime,
) {
    companion object {
        @JvmStatic
        fun from(entity: InterviewScriptEntity): InterviewScriptResponseV2 {
            return InterviewScriptResponseV2(
                pk = entity.pkValue,
                owner = entity.owner.pkValue,
                questionPk = entity.question.pkValue,
                content = entity.contentValue,
                createdTime = entity.createdTime,
                lastModifiedTime = entity.lastModifiedTime,
                lastReadTime = entity.lastReadTime,
            )
        }
    }
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
                pkValue = entity.pkValue,
                ownerPkValue = entity.owner.pkValue,
                question = question,
                contentValue = entity.contentValue,
                createdTimeValue = entity.createdTime,
                lastModifiedTimeValue = entity.lastModifiedTime,
                lastReadTimeValue = entity.lastReadTime,
            )
        }

        @JvmStatic
        fun of(
            scriptEntity: InterviewScriptEntity,
            questionResponse: InterviewQuestionResponse,
        ): InterviewScriptResponse {
            return InterviewScriptResponse(
                pkValue = scriptEntity.pkValue,
                ownerPkValue = scriptEntity.owner.pkValue,
                question = questionResponse,
                contentValue = scriptEntity.contentValue,
                createdTimeValue = scriptEntity.createdTime,
                lastModifiedTimeValue = scriptEntity.lastModifiedTime,
                lastReadTimeValue = scriptEntity.lastReadTime,
            )
        }
    }
}

data class InterviewScriptUpdateRequest(
    val contentValue: String,
) {
    fun getContent() = InterviewScript.Content(contentValue)
}
