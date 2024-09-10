package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.UnAuthorizedException
import org.idd.dia.domain.model.Member
import org.idd.dia.domain.model.MemberToken
import java.time.LocalDateTime

@Table(name = "member_tokens")
@Entity
class MemberTokenEntity(
    pk: MemberToken.Pk,
    accessToken: MemberToken.AccessToken,
    userAgent: MemberToken.UserAgent?,
    member: MemberEntity,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    @Column(name = "access_token")
    var accessTokenValue: String = accessToken.value
        protected set

    @Column(name = "user_agent", nullable = true)
    val userAgentValue: String? = userAgent?.value

    @ManyToOne
    @JoinColumn(nullable = false)
    val member: MemberEntity = member

    fun getOwnerPk(): Member.Pk = member.getPk()

    // todo: 자기 자신을 반환하는 것은 함수형 프로그래밍인데 패러다임이 혼재해도 되는걸까

    /**
     * userAgent가 있는 경우 userAgent를 비교하여 일치하는지 확인하고,
     * accessToken이 일치하는지 확인합니다.
     */
    fun validateAccessToken(
        accessToken: MemberToken.AccessToken,
        userAgent: MemberToken.UserAgent?,
    ): MemberTokenEntity {
        if (this.userAgentValue?.take(10) != userAgent?.value?.take(10)) {
            throw UnAuthorizedException("UserAgent is not matched")
        }
        if (this.accessTokenValue != accessToken.value) {
            throw UnAuthorizedException("Token is not matched")
        }
        return this
    }

    companion object {
        @JvmStatic
        fun new(
            accessToken: MemberToken.AccessToken,
            userAgent: MemberToken.UserAgent?,
            owner: MemberEntity,
            createdTime: LocalDateTime,
        ): MemberTokenEntity {
            return MemberTokenEntity(
                pk = MemberToken.Pk(0),
                accessToken = accessToken,
                userAgent = userAgent,
                member = owner,
                createdTime = createdTime,
            )
        }
    }
}
