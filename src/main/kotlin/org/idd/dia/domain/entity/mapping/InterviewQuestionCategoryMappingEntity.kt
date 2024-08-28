package org.idd.dia.domain.entity.mapping

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.entity.CommonEntity
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import java.time.LocalDateTime

@Table(name = "interview_question_category_mappings")
@Entity
class InterviewQuestionCategoryMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val question: InterviewQuestionEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val category: InterviewQuestionCategoryEntity,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    companion object {
        @JvmStatic
        fun new(
            question: InterviewQuestionEntity,
            category: InterviewQuestionCategoryEntity,
        ): InterviewQuestionCategoryMappingEntity =
            InterviewQuestionCategoryMappingEntity(
                pkValue = 0,
                question = question,
                category = category,
                createdTime = LocalDateTime.now(),
            )
    }
}
