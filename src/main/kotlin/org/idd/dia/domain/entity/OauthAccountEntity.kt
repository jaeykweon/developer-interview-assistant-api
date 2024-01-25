package org.idd.dia.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Table(name = "oauth_accounts")
@Entity
class OauthAccountEntity(
    pk: Long,
    member: MemberEntity,
    accessToken: String,
) {
    @Id
    val pk: Long = pk

    @OneToOne(fetch = FetchType.EAGER)
    val member: MemberEntity = member

    val accessToken: String = accessToken
}