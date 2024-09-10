package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewScriptReference
import org.idd.dia.domain.model.Member
import java.time.LocalDateTime

@Table(name = "interview_script_references")
@Entity
class InterviewScriptReferenceEntity(
    pk: InterviewScriptReference.Pk,
    memberPk: Member.Pk,
    url: InterviewScriptReference.Url,
    clickCount: InterviewScriptReference.ClickCount,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewScriptReference.Pk(pkValue)

    @Column(name = "member_pk", nullable = false)
    val memberPkValue: Long = memberPk.value

    @Column(name = "url", nullable = false)
    val urlValue: String = url.value

    @Column(name = "click_count", nullable = false)
    val clickCountValue: Long = clickCount.value
}
