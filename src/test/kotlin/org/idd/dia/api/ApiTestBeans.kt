package org.idd.dia.api

import org.idd.dia.adapter.external.SlackHandler
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Profile("test")
@Component
annotation class ApiTestBean

@ApiTestBean
internal class TestSlackHandler : SlackHandler {
    override fun sendErrorMessage(
        webRequest: WebRequest,
        error: Exception,
    ) {
    }
}
