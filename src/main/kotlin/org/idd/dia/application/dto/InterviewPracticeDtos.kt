package org.idd.dia.application.dto

import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewQuestionVoice

data class SingleInterviewPracticeResponse(
    val question: InterviewQuestion,
    val voices: Set<InterviewQuestionVoice>,
) {
    init {
        require(voices.isNotEmpty()) { "음성이 없습니다" }
        require(voicesMustSameQuestion()) { "문제 한 개의 음성 세트가 아닙니다" }
    }

    private fun voicesMustSameQuestion(): Boolean {
        return voices.all { voice ->
            voice.getQuestionPk() == question.getPk()
        }
    }
}
