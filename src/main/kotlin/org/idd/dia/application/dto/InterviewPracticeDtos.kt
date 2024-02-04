package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity
import org.idd.dia.util.mapToSet

data class SingleInterviewPracticeResponse(
    val question: InterviewQuestionResponse,
    val voices: Set<InterviewQuestionVoiceResponse>,
) {
    constructor(
        questionEntity: InterviewQuestionEntity,
        voiceEntities: Set<InterviewQuestionVoiceEntity>,
    ) : this(
        question = InterviewQuestionResponse.from(questionEntity),
        voices = voiceEntities.mapToSet { InterviewQuestionVoiceResponse(it) },
    )

    init {
        require(voices.isNotEmpty()) { "음성이 없습니다" }
        require(voicesMustSameQuestion()) { "문제 한 개의 음성 세트가 아닙니다" }
    }

    private fun voicesMustSameQuestion(): Boolean =
        voices.all { voice ->
            voice.questionPkValue == question.pkValue
        }
}
