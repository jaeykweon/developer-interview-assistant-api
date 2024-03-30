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
import org.idd.dia.domain.entity.InterviewQuestionCollectionEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import java.time.LocalDateTime

fun Collection<InterviewQuestionCollectionMappingEntity>.getQuestionPks(): List<InterviewQuestion.Pk> {
    return map { it.question.getPk() }
}

@Table(name = "interview_question_collection_mappings")
@Entity
class InterviewQuestionCollectionMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val collection: InterviewQuestionCollectionEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val question: InterviewQuestionEntity,
    @Column(name = "created_time", nullable = false)
    val createdTime: LocalDateTime,
)
