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
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.InterviewQuestion
import java.time.LocalDateTime

fun Iterable<InterviewQuestionBookmarkMappingEntity>.isBookmarked(interviewQuestionEntity: InterviewQuestionEntity): Boolean {
    return this.any { it.isBookmarked(interviewQuestionEntity) }
}

@Table(name = "interview_question_bookmark_mappings")
@Entity
class InterviewQuestionBookmarkMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val question: InterviewQuestionEntity,
    /**
     * todo: owner로 해도 될까 아니면 member로 해야할까?
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val owner: MemberEntity,
    @Column(name = "created_time", nullable = false)
    val createdTime: LocalDateTime,
) {
    fun getQuestionPk(): InterviewQuestion.Pk {
        return question.getPk()
    }

    companion object {
        fun new(
            question: InterviewQuestionEntity,
            owner: MemberEntity,
            createdTime: LocalDateTime,
        ): InterviewQuestionBookmarkMappingEntity {
            return InterviewQuestionBookmarkMappingEntity(
                pkValue = 0,
                question = question,
                owner = owner,
                createdTime = createdTime,
            )
        }
    }

    fun isBookmarked(interviewQuestionEntity: InterviewQuestionEntity): Boolean {
        return this.question.pkValue == interviewQuestionEntity.pkValue
    }
}
