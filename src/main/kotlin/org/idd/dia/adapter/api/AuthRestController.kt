package org.idd.dia.adapter.api

import org.idd.dia.application.dto.AuthTokenResponse
import org.idd.dia.application.service.AuthService
import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.model.MemberToken
import org.idd.dia.util.isNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@ApiV0RestController
class AuthRestController(
    private val authService: AuthService,
) {
    @GetMapping("/auth/oauth/github/callback")
    fun handleGithubOauthCallback(
        @RequestHeader("user-agent") userAgentValue: String? = null,
        @RequestParam code: String? = null,
    ): ApiResponse<AuthTokenResponse> {
        if (code.isNull()) {
            throw BadRequestException("code is null")
        }
        val token =
            authService.loginOrRegisterWithGithub(
                code = code,
                userAgent = userAgentValue?.let { MemberToken.UserAgent(it) },
            )
        return ApiResponse.ok(token)
    }
}
