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
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.Member
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
    memberPk: Member.Pk,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Column(name = "member_pk", nullable = false)
    val memberPkValue: Long = memberPk.value

    companion object {
        @JvmStatic
        fun new(
            question: InterviewQuestionEntity,
            memberPk: Member.Pk,
            createdTime: LocalDateTime,
        ): InterviewQuestionBookmarkMappingEntity {
            return InterviewQuestionBookmarkMappingEntity(
                pkValue = 0,
                question = question,
                memberPk = memberPk,
                createdTime = createdTime,
            )
        }
    }

    fun isBookmarked(interviewQuestionEntity: InterviewQuestionEntity): Boolean {
        return this.question.pkValue == interviewQuestionEntity.pkValue
    }
}
