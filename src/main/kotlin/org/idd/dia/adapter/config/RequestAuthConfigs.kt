package org.idd.dia.adapter.config

import org.idd.dia.application.service.AuthService
import org.idd.dia.domain.UnAuthorizedException
import org.idd.dia.domain.model.MemberToken
import org.idd.dia.util.isNull
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class RequestAuthArgumentResolverConfig(
    private val requestAuthResolver: RequestAuthResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(requestAuthResolver)
    }
}

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestAuth(val required: Boolean = true)

@Component
class RequestAuthResolver(
    private val authService: AuthService,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RequestAuth::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Long? {
        val requestAuth: RequestAuth = parameter.getParameterAnnotation(RequestAuth::class.java)!!
        val authorizationValue = webRequest.getHeader(HttpHeaders.AUTHORIZATION)
        val userAgentValue = webRequest.getHeader(HttpHeaders.USER_AGENT)

        if (authorizationValue.isNull()) {
            if (requestAuth.required) {
                throw UnAuthorizedException("Authorization header is required")
            }
            return null
        }

        val requestToken: MemberToken.AccessToken =
            MemberToken.AccessToken(authorizationValue)
        val userAgent: MemberToken.UserAgent? =
            userAgentValue?.let { MemberToken.UserAgent(it) }

        return authService
            .verifyToken(
                userAgent,
                requestToken,
            ).value
    }
}
