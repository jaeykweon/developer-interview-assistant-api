package org.idd.dia.application.dto

data class SingleInterviewPracticeResponse(
    val question: InterviewQuestionResponse,
    val voices: Set<InterviewQuestionVoiceResponse>,
) {
    init {
        require(voices.isNotEmpty()) { "음성이 없습니다" }
        require(voicesMustSameQuestion()) { "문제 한 개의 음성 세트가 아닙니다" }
    }

    private fun voicesMustSameQuestion(): Boolean =
        voices.all { voice ->
            voice.questionPkValue == question.pkValue
        }
}
