package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewQuestionRestController.getQuestions
 */
@DisplayName("면접 질문 목록 조회 API")
class InterviewQuestionsGetApiTest : ApiTest() {
    private val responseFieldDescriptors: List<FieldDescriptor> =
        commonResponseFields +
            pageResponseFields +
            InterviewQuestionGetApiTest.getResponseFields("data.pageData.[].")

    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .log().all()
                .filter(
                    document(
                        "get-interview-questions",
                        responseFields(responseFieldDescriptors),
                    ),
                )
                .`when`()
                .get("/api/v0/interview/questions?categoryValues=backend")

        response.statusCode shouldBe 200
    }
}
