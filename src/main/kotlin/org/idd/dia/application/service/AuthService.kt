package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.external.oauth.OauthHandler
import org.idd.dia.application.dto.AuthTokenResponse
import org.idd.dia.application.dto.GithubUserData
import org.idd.dia.application.port.usingcase.MemberTokenDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.MemberTokenEntity
import org.idd.dia.domain.model.Member
import org.idd.dia.domain.model.MemberToken
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val oauthHandler: OauthHandler,
    private val memberDbPort: MemberDbPort,
    private val memberTokenDbPort: MemberTokenDbPort,
) {
    fun verifyToken(
        userAgent: MemberToken.UserAgent?,
        accessToken: MemberToken.AccessToken,
    ): Member.Pk {
        return memberTokenDbPort
            .getEntity(accessToken)
            .validateAccessToken(
                accessToken = accessToken,
                userAgent = userAgent,
            ).getOwnerPk()
    }

    // todo: 한 ip에서 짧은 시간 내 여러번 요청하는 경우에 대한 핸들링 필요
    fun loginOrRegisterWithGithub(
        code: String,
        userAgent: MemberToken.UserAgent?,
    ): AuthTokenResponse {
        val githubUserData: GithubUserData = oauthHandler.getGithubUserData(code)

        registerIfMemberNotExists(githubUserData)

        val memberEntity: MemberEntity = memberDbPort.getEntity(githubUserData.githubId)
        val memberTokenEntity: MemberTokenEntity =
            issueAccessToken(
                ownerPk = memberEntity.getPk(),
                localDateTime = LocalDateTime.now(),
                userAgent = userAgent,
            )
        return AuthTokenResponse.fromTokenEntity(memberTokenEntity)
    }

    private fun registerIfMemberNotExists(githubUserData: GithubUserData) {
        if (memberDbPort.exists(githubUserData.githubId).not()) {
            memberDbPort.save(
                MemberEntity.fromGithub(
                    nickname = githubUserData.nickname,
                    oauthId = githubUserData.githubId,
                    imageUrl = githubUserData.imageUrl,
                ),
            )
        }
    }

    // 테스트를 위해 open으로 열어놓음
    fun issueAccessToken(
        ownerPk: Member.Pk,
        localDateTime: LocalDateTime,
        userAgent: MemberToken.UserAgent?,
    ): MemberTokenEntity {
        val memberEntity = memberDbPort.getEntity(ownerPk)
        val newAccessToken = issueAccessToken(memberEntity, localDateTime)
        return memberTokenDbPort.save(
            MemberTokenEntity.new(
                accessToken = newAccessToken,
                owner = memberEntity,
                userAgent = userAgent,
                createdTime = localDateTime,
            ),
        )
    }

    // todo: 토큰 생성 로직은 임시로 만듬. 추후 변경 필요
    private fun issueAccessToken(
        memberEntity: MemberEntity,
        localDateTime: LocalDateTime,
    ): MemberToken.AccessToken {
        return MemberToken.AccessToken(
            "ACCESS" +
                "@" +
                memberEntity.oauthIdValue +
                "@" +
                localDateTime.toString(),
        )
    }
}
