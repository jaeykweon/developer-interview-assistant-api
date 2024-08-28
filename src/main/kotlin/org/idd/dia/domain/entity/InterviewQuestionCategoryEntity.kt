package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewQuestionCategory
import java.time.LocalDateTime

@Table(name = "interview_question_categories")
@Entity
class InterviewQuestionCategoryEntity(
    pk: InterviewQuestionCategory.Pk,
    title: InterviewQuestionCategory.Title,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestionCategory.Pk(pkValue)

    @Column(name = "eng_title", nullable = false)
    val engTitleValue: String = title.value

    @Column(name = "kor_title", nullable = false)
    val korTitleValue: String = title.value
}
