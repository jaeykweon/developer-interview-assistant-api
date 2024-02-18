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
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeRestController.getPracticeHistories
 */
@DisplayName("면접 연습 히스토리 목록 조회 API")
class InterviewQuestionPracticeHistoriesGetApiTest : ApiTest() {
    private final val dataFields =
        listOf(
            fieldWithPath("data.scrollData.[].pkValue").type(NUMBER).description("면접 연습 히스토리 pkValue"),
            fieldWithPath("data.scrollData.[].typeValue").type(STRING).description("면접 연습 종료"),
            fieldWithPath("data.scrollData.[].contentValue").type(STRING).description("카테고리 pkValue"),
            fieldWithPath("data.scrollData.[].elapsedTimeValue").type(NUMBER).description("카테고리 이름"),
            fieldWithPath("data.scrollData.[].fileUrlValue").type(STRING).optional().description("카테고리 pkValue"),
            fieldWithPath("data.scrollData.[].createdTimeValue").type(STRING).description("면접 질문 pk 값"),
        )

    val responseFields: List<FieldDescriptor> =
        commonResponseFields +
            scrollResponseFields +
            dataFields +
            InterviewQuestionGetApiTest.getResponseFields("data.scrollData.[].question.")

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
