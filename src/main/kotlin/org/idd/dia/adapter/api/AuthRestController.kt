package org.idd.dia.adapter.api

import org.idd.dia.application.dto.AuthTokenResponse
import org.idd.dia.application.dto.GithubAuthorizationRequest
import org.idd.dia.application.service.AuthService
import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.model.MemberToken
import org.idd.dia.util.isNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV0RestController
class AuthRestController(
    private val authService: AuthService,
) {
    @PostMapping("/auth/oauth/github")
    fun authorizationByGithub(
        @RequestHeader("user-agent") userAgentValue: String? = null,
        @RequestBody githubAuthorizationRequest: GithubAuthorizationRequest,
    ): ApiResponse<AuthTokenResponse> {
        if (githubAuthorizationRequest.code.isNull()) {
            throw BadRequestException("code is null")
        }
        val token =
            authService.loginOrRegisterWithGithub(
                code = githubAuthorizationRequest.code,
                userAgent = userAgentValue?.let { MemberToken.UserAgent(it) },
            )
        return ApiResponse.ok(token)
    }
}
