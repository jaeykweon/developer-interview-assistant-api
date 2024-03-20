package org.idd.dia.adapter.external

import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.Field
import com.slack.api.webhook.Payload.PayloadBuilder
import com.slack.api.webhook.WebhookPayloads.payload
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.io.IOException

interface SlackHandler {
    fun sendErrorMessage(
        webRequest: WebRequest,
        error: Exception,
    )
}

@Profile("prod", "stag")
@Component
class RealSlackHandler(
    @Value("\${slack.webhook.url}")
    private val slackWebhookUrl: String,
) : SlackHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val slackClient = Slack.getInstance()

    @Async
    override fun sendErrorMessage(
        webRequest: WebRequest,
        error: Exception,
    ) {
        try {
            webRequest as ServletWebRequest

            val errorClass: Class<out Exception> = error::class.java
            val errorMessage: String = error.message ?: "에러 메세지가 없습니다"
            val title: String = "<${errorClass.simpleName}> $errorMessage"
            val content: String = error.extractStackTraceToStringWithLineBreak()
            val requestMethod: String = webRequest.request.method
            val requestUrl: StringBuffer = webRequest.request.requestURL
            val queryString: String = webRequest.request.queryString ?: ""

            slackClient.send(
                slackWebhookUrl,
                payload { p: PayloadBuilder ->
                    p
                        .text("*$title*")
                        .attachments(
                            listOf(
                                Attachment.builder().color("danger") // 메시지 색상
                                    .fields(
                                        listOf(
                                            generateSlackField(
                                                "[$requestMethod] $requestUrl?$queryString",
                                                content,
                                            ),
                                        ),
                                    ).build(),
                            ),
                        )
                },
            )
        } catch (e: IOException) {
            logger.error("${this::class.simpleName}", e)
        }
    }

    /**
     * Slack Field 생성
     */
    private fun generateSlackField(
        title: String,
        value: String,
    ): Field {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build()
    }

    private fun Exception.extractStackTraceToStringWithLineBreak(extractLines: Int = DEFAULT_STACK_TRACE_LOGGING_LINES): String {
        if (extractLines <= 0) {
            return this.stackTrace.take(DEFAULT_STACK_TRACE_LOGGING_LINES).joinToString("\n")
        }
        return this.stackTrace.take(extractLines).joinToString("\n")
    }

    companion object {
        private const val DEFAULT_STACK_TRACE_LOGGING_LINES = 7
    }
}

/**
 * local용
 */
@Profile("!prod&&!stag&&!test")
@Component
class FakeSlackHandler : SlackHandler {
    override fun sendErrorMessage(
        webRequest: WebRequest,
        error: Exception,
    ) {
        println("FakeSlackHandler.sendErrorMessage executed")
    }
}
