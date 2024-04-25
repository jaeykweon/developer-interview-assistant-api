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
 * @see org.idd.dia.adapter.api.InterviewQuestionRestController.postQuestionBookmark
 */
class InterviewQuestionBookmarkPostApiTest : ApiTest() {
    private val responseFields =
        listOf(
            fieldWithPath("data.bookmarked").description("북마크 등록/해제 결과").type(JsonFieldType.BOOLEAN),
        )

    private val responseFieldDescriptors: List<FieldDescriptor> =
        commonResponseFields + responseFields

    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .defaultAuthorization()
                .filter(
                    document(
                        "post-interview-question-bookmark",
                        pathParameters(
                            parameterWithName("questionPk").description("질문 pkValue"),
                        ),
                        requestHeaders(authRequired),
                        responseFields(responseFieldDescriptors),
                    ),
                )
                .`when`()
                .post("/api/v0/interview/questions/{questionPk}/bookmark", 1002)

        response.statusCode shouldBe 200
        response.body.jsonPath().getBoolean("data.bookmarked") shouldBe true
    }
}
