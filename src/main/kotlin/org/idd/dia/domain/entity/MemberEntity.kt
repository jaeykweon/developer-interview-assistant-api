package org.idd.dia.domain.entity

import org.idd.dia.domain.model.Member
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "dia_member")
@Entity
class MemberEntity(
    pk: Member.Pk,
    githubId: Member.GithubId,
    type: Member.Type,
) : DbEntity(pk = pk.value) {

    val githubId: String = githubId.value
}
