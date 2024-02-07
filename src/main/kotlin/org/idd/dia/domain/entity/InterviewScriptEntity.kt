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

@Table(name = "interview_script")
@Entity
class InterviewScriptEntity(
    pk: InterviewScript.Pk,
    owner: MemberEntity,
    question: InterviewQuestionEntity,
    content: InterviewScript.Content,
    createdTime: LocalDateTime,
    lastModifiedTime: LocalDateTime,
    lastReadTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewScript.Pk(pkValue)

    @JoinColumn(referencedColumnName = "pk", nullable = false)
    @OneToOne
    val owner: MemberEntity = owner

    @JoinColumn(referencedColumnName = "pk", nullable = false)
    @ManyToOne
    val question: InterviewQuestionEntity = question

    @Column(name = "content", nullable = false)
    var contentValue: String = content.value
        private set

    @Column(name = "created_time", nullable = false)
    val createdTime: LocalDateTime = createdTime

    @Column(nullable = false)
    var lastModifiedTime: LocalDateTime = lastModifiedTime
        private set

    @Column(nullable = false)
    var lastReadTime: LocalDateTime = lastReadTime
        private set

    fun read(time: LocalDateTime) {
        this.lastReadTime = time
    }

    fun updateContent(
        newContent: InterviewScript.Content,
        updateTime: LocalDateTime,
    ) {
        this.contentValue = newContent.value
        this.lastModifiedTime = updateTime
    }
}
