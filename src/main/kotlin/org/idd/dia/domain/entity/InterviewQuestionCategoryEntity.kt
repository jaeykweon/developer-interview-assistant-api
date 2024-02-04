package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewQuestionCategory

@Table(name = "interview_question_category")
@Entity
class InterviewQuestionCategoryEntity(
    pk: InterviewQuestionCategory.Pk,
    title: InterviewQuestionCategory.Title,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestionCategory.Pk(pkValue)

    @Column(name = "eng_title", nullable = false)
    val engTitleValue: String = title.value

    fun getEngTitle() = InterviewQuestionCategory.Title(engTitleValue)

    @Column(name = "kor_title", nullable = false)
    val korTitleValue: String = title.value

    fun getKorTitle() = InterviewQuestionCategory.Title(korTitleValue)
}
