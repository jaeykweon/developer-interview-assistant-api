package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewScript
import java.time.LocalDateTime

@Table(name = "interview_scripts")
@Entity
class InterviewScriptEntity(
    pk: InterviewScript.Pk,
    ownerEntity: MemberEntity,
    questionEntity: InterviewQuestionEntity,
    content: InterviewScript.Content,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewScript.Pk(pkValue)

    @OneToOne
    @JoinColumn(nullable = false)
    val owner: MemberEntity = ownerEntity

    @ManyToOne
    @JoinColumn(nullable = false)
    val question: InterviewQuestionEntity = questionEntity

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var contentValue: String = content.value
        protected set

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    var lastReadTime: LocalDateTime = createdTime
        protected set

    fun read(time: LocalDateTime) {
        this.lastReadTime = time
    }

    // todo: 함수형? 객체?
    fun updateContent(
        newContent: InterviewScript.Content,
        updateTime: LocalDateTime,
    ) {
        this.contentValue = newContent.value
        this.updatedTime = updateTime
    }

    companion object {
        @JvmStatic
        fun new(
            ownerEntity: MemberEntity,
            questionEntity: InterviewQuestionEntity,
            content: InterviewScript.Content,
            time: LocalDateTime,
        ): InterviewScriptEntity {
            return InterviewScriptEntity(
                pk = InterviewScript.Pk(),
                ownerEntity = ownerEntity,
                questionEntity = questionEntity,
                content = content,
                createdTime = time,
            )
        }
    }
}
