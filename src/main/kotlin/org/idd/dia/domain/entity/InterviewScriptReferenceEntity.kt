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

@Table(name = "interview_script_references")
@Entity
class InterviewScriptReferenceEntity(
    pk: InterviewScriptReference.Pk,
    ownerEntity: MemberEntity,
    url: InterviewScriptReference.Url,
    clickCount: InterviewScriptReference.ClickCount,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewScriptReference.Pk(pkValue)

    @ManyToOne
    @JoinColumn(nullable = false)
    val owner: MemberEntity = ownerEntity

    @Column(name = "url", nullable = false)
    val urlValue: String = url.value

    @Column(name = "click_count", nullable = false)
    val clickCountValue: Long = clickCount.value
}
