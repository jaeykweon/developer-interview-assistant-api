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
import org.idd.dia.domain.model.InterviewQuestionVoice
import java.time.LocalDateTime

@Table(name = "interview_question_voices")
@Entity
class InterviewQuestionVoiceEntity(
    pk: InterviewQuestionVoice.Pk,
    question: InterviewQuestionEntity,
    gender: Gender,
    filePath: InterviewQuestionVoice.FilePath,
    subtitle: InterviewQuestionVoice.SubTitle,
    createdTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestionVoice.Pk(pkValue)

    @ManyToOne
    @JoinColumn(nullable = false)
    val question: InterviewQuestionEntity = question

    @Column(name = "gender", nullable = false)
    val genderValue: String = gender.name

    @Column(name = "file_path", nullable = false)
    val filePathValue: String = filePath.value

    val fileUrlValue: String
        get() = "https://d2fu6egyyud2t8.cloudfront.net/$filePathValue"

    @Column(name = "subtitle", nullable = false)
    val subtitleValue: String = subtitle.value

    // todo: 마이그레이션 후 nullable 삭제
    @Column
    val createdTime: LocalDateTime? = createdTime
}
