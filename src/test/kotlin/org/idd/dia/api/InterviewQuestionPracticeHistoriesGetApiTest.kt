package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeHistoryRestController.getPracticeHistories
 */
@DisplayName("면접 연습 히스토리 목록 조회 API")
class InterviewQuestionPracticeHistoriesGetApiTest : ApiTest() {
    private val responseFieldDescriptors: List<FieldDescriptor> =
        commonResponseFields +
            scrollResponseFields +
            InterviewQuestionPracticeHistoryGetApiTest.getResponseFields(scrollDataPrefix)

    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .log().all()
                .defaultAuthorization()
                .filter(
                    document(
                        "get-interview-histories",
                        requestHeaders(authRequired),
                        responseFields(responseFieldDescriptors),
                    ),
                )
                .`when`()
                .get("api/v0/interview/practice/histories")

        response.statusCode shouldBe 200
    }
}
