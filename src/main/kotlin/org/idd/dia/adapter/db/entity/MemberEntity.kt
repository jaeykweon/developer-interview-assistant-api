package org.idd.dia.adapter.db.entity

import org.idd.dia.domain.Member
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "dia_member")
@Entity
class MemberEntity(
    pk: Member.Pk,
    githubId: Member.GithubId
) : DbEntity(pk = pk.value) {

    val githubId: String = githubId.value
}
