package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.Member
import java.time.LocalDateTime

@Table(name = "dia_members")
@Entity
class MemberEntity(
    pk: Member.Pk,
    nickname: Member.Nickname,
    oauthId: Member.OauthId,
    oauthProvider: Member.OauthProvider,
    imageUrl: Member.ImageUrl,
    createdTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = Member.Pk(pkValue)

    @Column(name = "nickname", nullable = false)
    val nicknameValue: String = nickname.value

    @Column(name = "image_url", nullable = false)
    val imageUrlValue: String = imageUrl.value

    @Column(name = "oauth_id", nullable = false)
    val oauthIdValue: String = oauthId.value

    @Column(name = "oauth_provider", nullable = false)
    val oauthProviderValue: String = oauthProvider.name

    @Column(nullable = false)
    val createdTime: LocalDateTime = createdTime

    @Column(nullable = false)
    var updatedTime: LocalDateTime = createdTime
        protected set

    companion object {
        @JvmStatic
        fun new(
            nickname: Member.Nickname,
            oauthId: Member.OauthId,
            oauthProvider: Member.OauthProvider,
            imageUrl: Member.ImageUrl,
            createdTime: LocalDateTime,
        ): MemberEntity {
            return MemberEntity(
                pk = Member.Pk(0),
                nickname = nickname,
                oauthId = oauthId,
                oauthProvider = oauthProvider,
                imageUrl = imageUrl,
                createdTime = createdTime,
            )
        }

        @JvmStatic
        fun fromGithub(
            nickname: Member.Nickname,
            oauthId: Member.OauthId,
            imageUrl: Member.ImageUrl,
        ): MemberEntity {
            return MemberEntity(
                pk = Member.Pk(0),
                nickname = nickname,
                oauthId = oauthId,
                oauthProvider = Member.OauthProvider.GITHUB,
                imageUrl = imageUrl,
                createdTime = LocalDateTime.now(),
            )
        }
    }
}
