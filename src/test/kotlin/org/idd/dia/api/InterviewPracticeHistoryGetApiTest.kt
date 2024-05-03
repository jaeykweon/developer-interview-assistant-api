package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeRestController.getPracticeHistory
 */
@DisplayName("면접 연습 히스토리 단 건 조회 API")
class InterviewPracticeHistoryGetApiTest : ApiTest() {
    private val responseFieldDescriptors: List<FieldDescriptor> =
        commonResponseFields +
            getResponseFields()

    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .log().all()
                .defaultAuthorization()
                .filter(
                    document(
                        "get-interview-history",
                        requestHeaders(authRequired),
                        responseFields(responseFieldDescriptors),
                    ),
                )
                .`when`()
                .get("api/v0/interview/practice/histories/1001")

        response.statusCode() shouldBe 200
    }

    companion object {
        @JvmStatic
        fun getResponseFields(prefix: String? = "data."): List<FieldDescriptor> {
            return listOf(
                fieldWithPath("${prefix}pkValue").type(NUMBER).description("연습 히스토리 pk"),
                fieldWithPath("${prefix}typeValue").type(STRING).description("연습 타입"),
                fieldWithPath("${prefix}contentValue").type(STRING).description("연습 내용"),
                fieldWithPath("${prefix}elapsedTimeValue").type(NUMBER).description("연습 시간"),
                fieldWithPath("${prefix}fileUrlValue").type(STRING).optional().description("음성 파일 url"),
                fieldWithPath("${prefix}starValue").type(Boolean).description("별표 여부"),
                fieldWithPath("${prefix}createdTimeValue").type(STRING).description("생성 시간"),
            ) + InterviewQuestionGetApiTest.getResponseFields("${prefix}question.")
        }
    }
}
