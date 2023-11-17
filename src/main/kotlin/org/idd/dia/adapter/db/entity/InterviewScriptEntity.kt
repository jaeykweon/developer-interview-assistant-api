package org.idd.dia.adapter.db.entity

import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.idd.dia.domain.Member
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "interview_script")
@Entity
class InterviewScriptEntity(
    pk: InterviewScript.Pk,
    ownerPk: Member.Pk,
    questionPk: InterviewQuestion.Pk,
    val content: InterviewScript.Content,
    val createdTime: LocalDateTime,
    val lastModifiedTime: LocalDateTime,
    val lastReadTime: LocalDateTime,
) : DbEntity(pk = pk.value) {

    @Column(nullable = false)
    val ownerPk: Long = ownerPk.value

    @Column(nullable = false)
    val questionPk: Long = questionPk.value
}
