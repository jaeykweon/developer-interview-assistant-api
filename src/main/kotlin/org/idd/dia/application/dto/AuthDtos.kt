package org.idd.dia.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.idd.dia.domain.entity.MemberTokenEntity
import org.idd.dia.domain.model.Member

data class GithubAccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    val scope: String,
)

data class GithubUserData(
    val login: String,
    val name: String,
    val id: Long,
    @JsonProperty("node_id")
    val nodeId: String,
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    @JsonProperty("html_url")
    val htmlUrl: String,
    val company: String?,
) {
    val githubId: Member.OauthId
        get() = Member.OauthId(login)

    val nickname: Member.Nickname
        get() = Member.Nickname(name)

    val imageUrl: Member.ImageUrl
        get() = Member.ImageUrl(avatarUrl)
}

data class AuthTokenResponse(
    val accessTokenValue: String,
) {
    companion object {
        @JvmStatic
        fun fromTokenEntity(memberTokenEntity: MemberTokenEntity): AuthTokenResponse {
            return AuthTokenResponse(
                accessTokenValue = memberTokenEntity.accessTokenValue,
            )
        }
    }
}
