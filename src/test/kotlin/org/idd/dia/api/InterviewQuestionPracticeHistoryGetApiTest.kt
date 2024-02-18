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
    private final val dataFields =
        listOf(
            fieldWithPath("data.pkValue").type(NUMBER).description("면접 연습 히스토리 pkValue"),
            fieldWithPath("data.typeValue").type(STRING).description("면접 연습 종료"),
            fieldWithPath("data.contentValue").type(STRING).description("카테고리 pkValue"),
            fieldWithPath("data.elapsedTimeValue").type(NUMBER).description("카테고리 이름"),
            fieldWithPath("data.fileUrlValue").type(STRING).optional().description("카테고리 pkValue"),
            fieldWithPath("data.createdTimeValue").type(STRING).description("면접 질문 pk 값"),
        )

    val responseFields: List<FieldDescriptor> =
        commonResponseFields +
            dataFields +
            InterviewQuestionGetApiTest.getResponseFields("data.question.")

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
                            parameterWithName("pkValue").description("면접 연습 pkValue"),
                        ),
                        responseFields(responseFields),
                    ),
                )
                .`when`()
                .get("api/v0/interview/practice/histories/{pkValue}", 1001)

        response.statusCode shouldBe 200
    }
}
