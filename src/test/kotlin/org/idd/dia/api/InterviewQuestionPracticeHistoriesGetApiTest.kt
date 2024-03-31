package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeRestController.getPracticeHistories
 */
@DisplayName("면접 연습 히스토리 목록 조회 API")
class InterviewQuestionPracticeHistoriesGetApiTest : ApiTest() {
    val responseFields: List<FieldDescriptor> =
        commonResponseFields +
            scrollResponseFields +
            InterviewQuestionPracticeHistoryGetApiTest.getResponseFields(scrollDataPrefix) +
            InterviewQuestionGetApiTest.getResponseFields("${scrollDataPrefix}question.")

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
                        responseFields(responseFields),
                    ),
                )
                .`when`()
                .get("api/v0/interview/practice/histories")

        response.statusCode shouldBe 200
    }
}
