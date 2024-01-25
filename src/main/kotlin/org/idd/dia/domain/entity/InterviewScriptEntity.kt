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

    fun getContent() = InterviewScript.Content(contentValue)

    @Column(name = "created_time", nullable = false)
    private val createdTime: LocalDateTime = createdTime

    fun getCreatedTime() = createdTime

    @Column(nullable = false)
    private var lastModifiedTime: LocalDateTime = lastModifiedTime

    fun getLastModifiedTime() = lastModifiedTime

    @Column(nullable = false)
    private var lastReadTime: LocalDateTime = lastReadTime

    fun getLastReadTime() = lastReadTime

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
