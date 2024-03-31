package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeRestController.getPracticeHistory
 */
@DisplayName("면접 연습 히스토리 단 건 조회 API")
class InterviewQuestionPracticeHistoryGetApiTest : ApiTest() {
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
                        pathParameters(
                            parameterWithName("pk").description("면접 연습 pkValue"),
                        ),
                        responseFields(responseFieldDescriptors),
                    ),
                )
                .`when`()
                .get("api/v0/interview/practice/histories/{pk}", 1001)

        response.statusCode shouldBe 200
    }

    companion object {
        @JvmStatic
        fun getResponseFields(prefix: String? = "data."): List<FieldDescriptor> {
            return listOf(
                fieldWithPath("${prefix}pkValue").type(NUMBER).description("면접 연습 히스토리 pkValue"),
                fieldWithPath("${prefix}typeValue").type(STRING).description("면접 연습 종료"),
                fieldWithPath("${prefix}contentValue").type(STRING).description("카테고리 pkValue"),
                fieldWithPath("${prefix}elapsedTimeValue").type(NUMBER).description("카테고리 이름"),
                fieldWithPath("${prefix}fileUrlValue").type(STRING).optional().description("카테고리 pkValue"),
                fieldWithPath("${prefix}starValue").type(Boolean).description("면접 연습 히스토리 별표 여부"),
                fieldWithPath("${prefix}createdTimeValue").type(STRING).description("면접 질문 pk 값"),
            ) + InterviewQuestionGetApiTest.getResponseFields("${prefix}question.")
        }
    }
}
