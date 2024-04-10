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
                        scriptResponse.questionPkValue == questionResponse.pkValue
                    }
                return@map ofMember(questionResponse, targetScript)
            }
        }
    }
}

// todo: InterviewScriptResponse 를 이거로 변경
data class InterviewScriptResponseV2(
    val pkValue: Long,
    val ownerPkValue: Long,
    val questionPkValue: Long,
    val contentValue: String,
    val createdTimeValue: LocalDateTime,
    val lastModifiedTimeValue: LocalDateTime,
    val lastReadTimeValue: LocalDateTime,
) {
    companion object {
        @JvmStatic
        fun from(entity: InterviewScriptEntity): InterviewScriptResponseV2 {
            return InterviewScriptResponseV2(
                pkValue = entity.pkValue,
                ownerPkValue = entity.owner.pkValue,
                questionPkValue = entity.question.pkValue,
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
