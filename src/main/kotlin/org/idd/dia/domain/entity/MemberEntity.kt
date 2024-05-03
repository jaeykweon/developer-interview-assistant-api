package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.Member

@Table(name = "dia_members")
@Entity
class MemberEntity(
    pk: Member.Pk,
    nickname: Member.Nickname,
    oauthId: Member.OauthId,
    oauthProvider: Member.OauthProvider,
    imageUrl: Member.ImageUrl,
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

    // todo: db migration 완료되면, nullable false로 변경
    @Column(name = "oauth_id")
    val oauthIdValue: String = oauthId.value

    // todo: db migration 완료되면, nullable false로 변경
    @Column(name = "oauth_provider")
    val oauthProviderValue: String = oauthProvider.name

    companion object {
        @JvmStatic
        fun new(
            nickname: Member.Nickname,
            oauthId: Member.OauthId,
            oauthProvider: Member.OauthProvider,
            imageUrl: Member.ImageUrl,
        ): MemberEntity {
            return MemberEntity(
                pk = Member.Pk(0),
                nickname = nickname,
                oauthId = oauthId,
                oauthProvider = oauthProvider,
                imageUrl = imageUrl,
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
            )
        }
    }
}
