package org.idd.dia.domain.model

import java.time.LocalDateTime

class InterviewPracticeHistory(
    pk: Pk,
    memberPk: Member.Pk,
    questionPk: InterviewQuestion.Pk,
    voicePk: InterviewQuestionVoice.Pk,
    time: LocalDateTime
) {
    val pk: Pk = pk
    val memberPk: Member.Pk
    val questionPk: InterviewQuestion.Pk
    val voicePk: InterviewQuestionVoice.Pk
    val time: LocalDateTime

    @JvmInline
    value class Pk(
        val value: Long
    )
}
