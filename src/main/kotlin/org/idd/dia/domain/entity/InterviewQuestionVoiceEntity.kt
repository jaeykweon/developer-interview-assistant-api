package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.model.Gender
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionVoice

@Table(name = "interview_question_voice")
@Entity
class InterviewQuestionVoiceEntity(
    pk: InterviewQuestionVoice.Pk,
    question: InterviewQuestionEntity,
    questionPk: InterviewQuestion.Pk,
    gender: Gender,
    filePath: InterviewQuestionVoice.FilePath,
    subtitle: InterviewQuestionVoice.SubTitle,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestionVoice.Pk(pkValue)

    @ManyToOne
    @JoinColumn(referencedColumnName = "pk",nullable = false)
    val question: InterviewQuestionEntity = question

    @Column(name = "gender", nullable = false)
    val genderValue: String = gender.name

    fun getGender() = Gender.from(genderValue)

    @Column(name = "file_path", nullable = false)
    val filePathValue: String = filePath.value

    fun getFilePath() = InterviewQuestionVoice.FilePath(filePathValue)

    @Column(name = "subtitle", nullable = false)
    val subtitleValue: String = subtitle.value

    fun getSubtitle() = InterviewQuestionVoice.SubTitle(subtitleValue)
}
