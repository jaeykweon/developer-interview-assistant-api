package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeHistoryRestController.recordPracticeHistory
 */
@DisplayName("면접 연습 히스토리 등록 API")
class InterviewPracticeHistoryPostApiTest : ApiTest() {
    private val request =
        RecordInterviewPracticeRequest(
            interviewQuestionPkValue = 1001,
            contentValue = "테스트",
            typeValue = "single",
            elapsedTimeValue = 300,
            filePathValue = null,
        )

    private val requestFields =
        listOf(
            fieldWithPath("interviewQuestionPkValue").type(NUMBER).description("면접 질문 pkValue"),
            fieldWithPath("contentValue").type(STRING).description("면접 연습 내용"),
            fieldWithPath("typeValue").type(STRING).description("면접 연습 종류"),
            fieldWithPath("elapsedTimeValue").type(NUMBER).description("면접 연습 시간"),
            fieldWithPath("filePathValue").type(STRING).optional().description("면접 연습 파일 경로"),
        )

    private final val responseDataFields =
        listOf(fieldWithPath("data").type(NUMBER).description("면접 연습 히스토리 pkValue"))

    private val responseFields: List<FieldDescriptor> =
        commonResponseFields + responseDataFields

    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            RestAssured.given(this.spec)
                .contentType(ContentType.JSON)
                .defaultAuthorization()
                .filter(
                    document(
                        "register-interview-practice-history",
                        requestHeaders(authRequired),
                        requestFields(requestFields),
                        responseFields(responseFields),
                    ),
                )
                .body(gson.toJson(request))
                .`when`()
                .post("/api/v0/interview/practice/histories")

        response.statusCode shouldBe 200
    }
}
