package org.idd.dia.adapter.external.oauth

import org.idd.dia.application.dto.GithubAccessTokenResponse
import org.idd.dia.application.dto.GithubUserData
import org.idd.dia.domain.ServiceUnavailableException
import org.idd.dia.domain.UnAuthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

interface GithubOauthHandler {
    fun getGithubUserData(code: String): GithubUserData
}

@Component
class RealGithubOauthHandler(
    @Value("\${oauth.github.client-id}")
    private val githubClientId: String,
    @Value("\${oauth.github.client-secret}")
    private val githubClientSecret: String,
) : GithubOauthHandler {
    override fun getGithubUserData(code: String): GithubUserData {
        val accessTokenResponse =
            WebClient.create()
                .post()
                .uri("https://github.com/login/oauth/access_token")
                .bodyValue(
                    mapOf(
                        "code" to code,
                        "client_id" to githubClientId,
                        "client_secret" to githubClientSecret,
                    ),
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubAccessTokenResponse::class.java)
                .retry(0)
                .onErrorMap { error ->
                    // todo: 로깅이 정해지면 에러 로깅
                    throw ServiceUnavailableException("github oauth access token 요청 중 에러 발생")
                }
                .block(Duration.ofSeconds(3000L))
                ?: throw UnAuthorizedException("github oauth access token 요청 중 에러 발생")

        return WebClient.create()
            .get()
            .uri("https://api.github.com/user")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${accessTokenResponse.accessToken}")
            .retrieve()
            .bodyToMono(GithubUserData::class.java)
            .block()
            ?: throw UnAuthorizedException("github user 정보 요청 중 에러 발생")
    }
}
