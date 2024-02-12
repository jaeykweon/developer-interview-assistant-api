package org.idd.dia.domain.entity

import jakarta.persistence.Column
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

    @OneToOne(fetch = FetchType.LAZY)
    val member: MemberEntity = member

    @Column(name = "access_token")
    val accessToken: String = accessToken
}
