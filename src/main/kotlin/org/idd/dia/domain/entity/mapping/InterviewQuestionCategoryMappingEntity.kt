package org.idd.dia.domain.entity.mapping

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity

@Entity
@Table(name = "interview_question_category_mapping")
class InterviewQuestionCategoryMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: Long,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_question_pk", nullable = false)
    val question: InterviewQuestionEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_question_category_pk", nullable = false)
    val category: InterviewQuestionCategoryEntity,
)
