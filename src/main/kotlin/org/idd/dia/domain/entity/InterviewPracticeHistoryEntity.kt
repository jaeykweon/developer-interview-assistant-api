package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewPracticeHistory
import java.time.LocalDateTime

@Entity
@Table(name = "interview_practice_history")
class InterviewPracticeHistoryEntity(
    pk: InterviewPracticeHistory.Pk,
    owner: MemberEntity,
    question: InterviewQuestionEntity,
    content: InterviewPracticeHistory.Content,
    type: InterviewPracticeHistory.Type,
    elapsedTime: InterviewPracticeHistory.ElapsedTime,
    filePath: InterviewPracticeHistory.FilePath?,
    createdTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewPracticeHistory.Pk(pkValue)

    @ManyToOne
    @JoinColumn(referencedColumnName = "pk", nullable = false)
    val owner: MemberEntity = owner

    @ManyToOne
    @JoinColumn(referencedColumnName = "pk", nullable = false)
    val question: InterviewQuestionEntity = question

    fun getQuestionPk() = question.getPk()

    @Column(name = "content", nullable = false)
    val contentValue: String = content.value

    @Column(name = "type", nullable = false)
    val typeValue = type.name

    @Column(name = "elapsed_time", nullable = false)
    val elapsedTimeValue = elapsedTime.value

    @Column(name = "file_path", nullable = true)
    val filePath: String? = filePath?.value

    @Column(name = "created_time", nullable = false)
    val createdTime: LocalDateTime = createdTime

    companion object {
        @JvmStatic
        fun new(
            owner: MemberEntity,
            question: InterviewQuestionEntity,
            content: InterviewPracticeHistory.Content,
            type: InterviewPracticeHistory.Type,
            elapsedTime: InterviewPracticeHistory.ElapsedTime,
            filePath: InterviewPracticeHistory.FilePath?,
            createdTime: LocalDateTime,
        ): InterviewPracticeHistoryEntity {
            return InterviewPracticeHistoryEntity(
                pk = InterviewPracticeHistory.Pk.new(),
                owner = owner,
                question = question,
                content = content,
                type = type,
                elapsedTime = elapsedTime,
                filePath = filePath,
                createdTime = createdTime,
            )
        }
    }
}
