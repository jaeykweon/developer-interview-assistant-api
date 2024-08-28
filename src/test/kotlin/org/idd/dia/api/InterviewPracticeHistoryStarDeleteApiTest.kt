package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeHistoryRestController.deleteStarPracticeHistory
 */
@DisplayName("면접 연습 히스토리 별표 해제 API")
class InterviewPracticeHistoryStarDeleteApiTest : ApiTest() {
    private val responseFields =
        listOf(
            fieldWithPath("data.stared").description("별표 등록/해제 결과").type(JsonFieldType.BOOLEAN),
        )

    private val responseFieldDescriptors: List<FieldDescriptor> =
        commonResponseFields +
            responseFields

    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .defaultAuthorization()
                .filter(
                    document(
                        "delete-interview-practice-history-star",
                        pathParameters(
                            parameterWithName("practiceHistoryPk").description("면접 연습 기록 pkValue"),
                        ),
                        requestHeaders(authRequired),
                        responseFields(responseFieldDescriptors),
                    ),
                )
                .`when`()
                .delete("/api/v0/interview/practice/histories/{practiceHistoryPk}/star", 1002)

        response.statusCode shouldBe 200
        response.body.jsonPath().getBoolean("data.stared") shouldBe false
    }
}
