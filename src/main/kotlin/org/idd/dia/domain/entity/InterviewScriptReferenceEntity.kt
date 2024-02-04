package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewScriptReference

@Table(name = "interview_script_reference")
@Entity
class InterviewScriptReferenceEntity(
    pk: InterviewScriptReference.Pk,
    owner: MemberEntity,
    url: InterviewScriptReference.Url,
    clickCount: InterviewScriptReference.ClickCount,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewScriptReference.Pk(pkValue)

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    val owner: MemberEntity = owner

    @Column(name = "url", nullable = false)
    val urlValue: String = url.value

    fun getUrl() = InterviewScriptReference.Url(urlValue)

    @Column(name = "click_count", nullable = false)
    val clickCountValue: Long = clickCount.value

    fun getClickCount() = InterviewScriptReference.ClickCount(clickCountValue)
}
