package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity

data class InterviewQuestionVoiceResponse(
    val pk: Long,
    val questionPk: Long,
    val gender: String,
    val fileUrl: String,
) {
    constructor(voice: InterviewQuestionVoiceEntity) : this(
        pk = voice.getPk().value,
        questionPk = voice.question.pkValue,
        gender = voice.getGender().name,
        fileUrl = voice.getFilePath().value,
    )
}
