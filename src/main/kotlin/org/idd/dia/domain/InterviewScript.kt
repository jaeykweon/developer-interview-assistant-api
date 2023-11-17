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

    private fun authenticate(requestMemberPk: Member.Pk) {
        require(requestMemberPk == ownerPk) { throw UnAuthorizedException() }
    }

    // todo: 여기서 까지 authenticate를 하는 것이 맞나?
    fun readContent(requestMemberPk: Member.Pk, readTime: LocalDateTime): InterviewScript {
        authenticate(requestMemberPk)
        this.lastReadTime = readTime
        return this
    }

    fun updateContent(
        requestMemberPk: Member.Pk,
        newContent: Content,
        updateTime: LocalDateTime
    ): InterviewScript {
        authenticate(requestMemberPk)
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
    )
}
