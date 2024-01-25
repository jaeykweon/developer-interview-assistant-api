package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewQuestionCategory

@Table(name="interview_question_category")
@Entity
class InterviewQuestionCategoryEntity(
    pk: InterviewQuestionCategory.Pk,
    korTitle: InterviewQuestionCategory.KorTitle,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestionCategory.Pk(pkValue)

    @Column(name = "kor_title", nullable = false)
    val korTitleValue: String = korTitle.value

    fun getKorTitle() = InterviewQuestionCategory.KorTitle(korTitleValue)
}
