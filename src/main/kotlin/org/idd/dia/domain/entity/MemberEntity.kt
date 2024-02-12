package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.Member

@Table(name = "dia_member")
@Entity
class MemberEntity(
    pk: Member.Pk,
    nickname: Member.Nickname,
    githubId: Member.GithubId,
    imageUrl: Member.ImageUrl,
) : Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = Member.Pk(pkValue)

    @Column(name = "nickname", nullable = false)
    val nicknameValue: String = nickname.value

    @Column(name = "image_url", nullable = false)
    val imageUrlValue: String = imageUrl.value

    @Column(name = "github_id", nullable = false)
    val githubIdValue: String = githubId.value
}
