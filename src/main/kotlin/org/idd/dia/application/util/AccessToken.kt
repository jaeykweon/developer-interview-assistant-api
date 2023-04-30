package org.idd.dia.application.util

import org.idd.dia.domain.DiaUserPk
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestAuthorization

interface AuthToken {
    fun getTokenString(): String
}

data class AuthJwt(
    private val value: String
) : AuthToken {
    override fun getTokenString(): String {
        return value
    }
}

data class AccessTokenContent(
    val diaUserPk: DiaUserPk
)

@Component
class AuthJwtResolver(
    private val authProvider: AuthProvider,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RequestAuthorization::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AccessTokenContent {
        val authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION)
            ?: throw IllegalArgumentException()

        val authJwt = splitToTokenFormat(authorization)

        return authProvider.verify(authJwt)
    }

    private fun splitToTokenFormat(authorization: String): AuthJwt {
        if (authorization.startsWith(BEARER).not()) return AuthJwt(authorization)

        val splitted = try {
            val tokenFormat = authorization.split(" ")
            tokenFormat[0] to tokenFormat[1]
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException()
        }

        return AuthJwt(splitted.second)
    }

    companion object {
        private const val BEARER = "Bearer"
    }
}
