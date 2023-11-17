package org.idd.dia.domain

import java.time.LocalDateTime

class InterviewScript(
    private val pk: Pk,
    private val ownerPk: Member.Pk,
    private val questionPk: InterviewQuestion.Pk,
    private var content: Content,
    private val createdTime: LocalDateTime,
    private var lastModifiedTime: LocalDateTime,
    private var lastReadTime: LocalDateTime
) {
    fun getPk() = this.pk
    fun getOwnerPk() = this.ownerPk
    fun getQuestionPk() = this.questionPk
    fun getContent() = this.content
    fun getCreatedTime() = this.createdTime
    fun getLastModifiedTime() = this.lastModifiedTime
    fun getLastReadTime() = this.lastReadTime

    fun authenticate(requestMemberPk: Member.Pk) {
        require(requestMemberPk == ownerPk) { throw UnAuthorizedException() }
    }

    fun readContent(readTime: LocalDateTime): InterviewScript {
        this.lastReadTime = readTime
        return this
    }

    fun updateContent(
        newContent: Content,
        updateTime: LocalDateTime
    ): InterviewScript {
        this.content = newContent
        this.lastModifiedTime = updateTime
        this.lastReadTime = updateTime
        return this
    }

    @JvmInline
    value class Pk(
        val value: Long = 0L
    )

    @JvmInline
    value class Content(
        val value: String
    ) {
        init { require(value.length <10000) }
    }
}
