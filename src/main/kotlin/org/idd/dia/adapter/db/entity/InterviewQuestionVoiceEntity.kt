package org.idd.dia.adapter.db.entity

import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewQuestionVoice
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Table(name = "interview_question_voice")
@Entity
class InterviewQuestionVoiceEntity(
    pk: InterviewQuestionVoice.Pk,
    questionPk: InterviewQuestion.Pk,
    gender: InterviewQuestionVoice.Gender,
    filePath: InterviewQuestionVoice.FilePath
) : DbEntity(pk = pk.value) {

    val questionPk: Long = questionPk.value

    @Enumerated(EnumType.STRING)
    val gender: InterviewQuestionVoice.Gender = gender

    val filePath: String = filePath.value
}
