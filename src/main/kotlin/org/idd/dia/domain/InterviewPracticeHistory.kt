package org.idd.dia.domain

import java.time.LocalDateTime

class InterviewPracticeHistory(
    val pk: Pk,
    val memberPk: Member.Pk,
    val questionPk: InterviewQuestion.Pk,
    val voicePk: InterviewQuestionVoice.Pk,
    val time: LocalDateTime
) {
    @JvmInline
    value class Pk(
        val value: Long
    )
}
