package org.idd.dia.domain.entity

import org.idd.dia.domain.model.InterviewScriptReference
import org.idd.dia.domain.model.Member
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "interview_script_reference")
@Entity
class InterviewScriptReferenceEntity(
    pk: InterviewScriptReference.Pk,
    ownerPk: Member.Pk,
    url: InterviewScriptReference.Url,
    clickCount: InterviewScriptReference.ClickCount
) : DbEntity(pk = pk.value) {

    val ownerPk: Long = ownerPk.value

    val url: String = url.value

    val clickCount: Long = clickCount.value
}
