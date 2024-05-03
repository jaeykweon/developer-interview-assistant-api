package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity

data class InterviewQuestionVoiceResponse(
    val pkValue: Long,
    val questionPkValue: Long,
    val genderValue: String,
    val fileUrlValue: String,
) {
    constructor(voice: InterviewQuestionVoiceEntity) : this(
        pkValue = voice.getPk().value,
        questionPkValue = voice.question.pkValue,
        genderValue = voice.genderValue,
        fileUrlValue = voice.fileUrlValue,
    )
}
