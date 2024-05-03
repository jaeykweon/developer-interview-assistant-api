package org.idd.dia.adapter.external.oauth

import org.idd.dia.application.dto.GithubUserData
import org.springframework.stereotype.Component

interface OauthHandler {
    fun getGithubUserData(code: String): GithubUserData
}

@Component
class RealOauthHandler(
    private val githubOauthHandler: GithubOauthHandler,
) : OauthHandler {
    override fun getGithubUserData(code: String): GithubUserData {
        return githubOauthHandler.getGithubUserData(code)
    }
}
