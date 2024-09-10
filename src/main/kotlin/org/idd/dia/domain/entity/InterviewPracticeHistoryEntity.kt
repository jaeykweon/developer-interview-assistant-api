package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.UnAuthorizedException
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import java.time.LocalDateTime

@Table(name = "interview_practice_histories")
@Entity
class InterviewPracticeHistoryEntity(
    pk: InterviewPracticeHistory.Pk,
    memberPk: Member.Pk,
    questionEntity: InterviewQuestionEntity,
    content: InterviewPracticeHistory.Content,
    type: InterviewPracticeHistory.Type,
    elapsedTime: InterviewPracticeHistory.ElapsedTime,
    filePath: InterviewPracticeHistory.FilePath?,
    star: Boolean,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk(): InterviewPracticeHistory.Pk = InterviewPracticeHistory.Pk(pkValue)

    @Column(name = "member_pk", nullable = false)
    val memberPkValue: Long = memberPk.value

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

    @Column(name = "deleted")
    var deletedValue: Boolean = false
        protected set

    fun star() {
        this.starValue = true
    }

    fun unStar() {
        this.starValue = false
    }

    fun validateOwner(memberPk: Member.Pk) {
        if (memberPk.value != memberPkValue) {
            throw UnAuthorizedException("Owner mismatch. memberPk: $memberPk, owner: $memberPkValue")
        }
    }

    fun delete(memberPk: Member.Pk) {
        validateOwner(memberPk)
        deletedValue = true
    }

    companion object {
        @JvmStatic
        fun new(
            memberPk: Member.Pk,
            questionEntity: InterviewQuestionEntity,
            content: InterviewPracticeHistory.Content,
            type: InterviewPracticeHistory.Type,
            elapsedTime: InterviewPracticeHistory.ElapsedTime,
            filePath: InterviewPracticeHistory.FilePath?,
            createdTime: LocalDateTime,
        ): InterviewPracticeHistoryEntity {
            return InterviewPracticeHistoryEntity(
                pk = InterviewPracticeHistory.Pk(0),
                memberPk = memberPk,
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
