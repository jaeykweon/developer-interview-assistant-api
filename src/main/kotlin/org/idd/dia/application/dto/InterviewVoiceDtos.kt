package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity
import org.idd.dia.domain.model.InterviewQuestionVoice

data class InterviewVoiceResponse(
    val pk: Long,
    val questionPk: Long,
    val gender: InterviewQuestionVoice.Gender,
    val filePath: InterviewQuestionVoice.FilePath,
    val subtitle: InterviewQuestionVoice.SubTitle

) {
    constructor(entity: InterviewQuestionVoiceEntity): this(
        pk = entity.pk,
        questionPk = entity.questionPk,
        gender = entity.getGender(),
        filePath = entity.filePath,
        subtitle = entity.subtitle
    )

}
