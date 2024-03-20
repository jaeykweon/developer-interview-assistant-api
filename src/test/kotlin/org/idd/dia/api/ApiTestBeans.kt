package org.idd.dia.api

import org.idd.dia.adapter.external.SlackHandler
import org.idd.dia.adapter.external.oauth.GithubOauthHandler
import org.idd.dia.application.dto.GithubUserData
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Profile("test")
@Primary
@Component
annotation class ApiTestBean

@ApiTestBean
internal class TestSlackHandler : SlackHandler {
    override fun sendErrorMessage(
        webRequest: WebRequest,
        error: Exception,
    ) {
        println("TestSlackHandler.sendErrorMessage executed")
    }
}

@ApiTestBean
internal class TestGithubOauthHandler : GithubOauthHandler {
    private var githubIdNumber = 1L

    /**
     * 테스트에서는 code를 그대로 닉네임과 이름으로 사용합니다
     */
    override fun getGithubUserData(code: String): GithubUserData {
        println("TestGithubOauthHandler.getGithubUserData executed")
        return GithubUserData(
            login = code,
            name = code,
            id = githubIdNumber++,
            avatarUrl = "test$githubIdNumber",
            htmlUrl = "test$githubIdNumber",
            company = "test",
            nodeId = "test$githubIdNumber",
        )
    }
}
