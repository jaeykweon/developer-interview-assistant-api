package org.idd.dia.domain.entity

import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionVoice
import javax.persistence.Column
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
    filePath: InterviewQuestionVoice.FilePath,
    subtitle: InterviewQuestionVoice.SubTitle
) : DbEntity(pk = pk.value) {

    val questionPk: Long = questionPk.value

    private val gender: String = gender.name
    fun getGender() = InterviewQuestionVoice.Gender.from(gender)

    val filePath: String = filePath.value

    val subtitle: String = subtitle.value
}
