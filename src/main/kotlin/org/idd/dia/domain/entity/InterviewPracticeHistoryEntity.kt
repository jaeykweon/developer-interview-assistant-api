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

@Table(name = "interview_practice_histories")
@Entity
class InterviewPracticeHistoryEntity(
    pk: InterviewPracticeHistory.Pk,
    ownerEntity: MemberEntity,
    questionEntity: InterviewQuestionEntity,
    content: InterviewPracticeHistory.Content,
    type: InterviewPracticeHistory.Type,
    elapsedTime: InterviewPracticeHistory.ElapsedTime,
    filePath: InterviewPracticeHistory.FilePath?,
    star: Boolean,
    createdTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk(): InterviewPracticeHistory.Pk = InterviewPracticeHistory.Pk(pkValue)

    @ManyToOne
    @JoinColumn(nullable = false)
    val owner: MemberEntity = ownerEntity

    @ManyToOne
    @JoinColumn(nullable = false)
    val question: InterviewQuestionEntity = questionEntity

    fun getQuestionPk() = question.getPk()

    @Column(name = "content", nullable = false)
    val contentValue: String = content.value

    @Column(name = "type", nullable = false)
    val typeValue: String = type.name

    @Column(name = "elapsed_time", nullable = false)
    val elapsedTimeValue: Int = elapsedTime.value

    @Column(name = "file_path", nullable = true)
    val filePathValue: String? = filePath?.value

    // todo: db 마이그레이션 후 nullable false로 변경
    @Column(name = "star")
    var starValue: Boolean = star
        protected set

    @Column(name = "created_time", nullable = false)
    val createdTime: LocalDateTime = createdTime

    fun star() {
        this.starValue = true
    }

    fun unStar() {
        this.starValue = false
    }

    companion object {
        @JvmStatic
        fun new(
            ownerEntity: MemberEntity,
            questionEntity: InterviewQuestionEntity,
            content: InterviewPracticeHistory.Content,
            type: InterviewPracticeHistory.Type,
            elapsedTime: InterviewPracticeHistory.ElapsedTime,
            filePath: InterviewPracticeHistory.FilePath?,
            createdTime: LocalDateTime,
        ): InterviewPracticeHistoryEntity {
            return InterviewPracticeHistoryEntity(
                pk = InterviewPracticeHistory.Pk(0),
                ownerEntity = ownerEntity,
                questionEntity = questionEntity,
                content = content,
                type = type,
                elapsedTime = elapsedTime,
                filePath = filePath,
                star = false,
                createdTime = createdTime,
            )
        }
    }
}
