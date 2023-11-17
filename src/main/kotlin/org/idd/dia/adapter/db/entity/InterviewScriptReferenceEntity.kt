package org.idd.dia.adapter.db.entity

import org.idd.dia.domain.InterviewScriptReference
import org.idd.dia.domain.Member
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
