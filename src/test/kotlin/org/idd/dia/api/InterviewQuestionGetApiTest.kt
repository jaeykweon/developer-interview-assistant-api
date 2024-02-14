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

class InterviewQuestionGetApiTest : ApiTestCommon() {
    val responseFields: List<FieldDescriptor> =
        listOf(
            fieldWithPath("status").type(NUMBER).description("응답 상태"),
            fieldWithPath("detail").type(NUMBER).optional().description("디테일"),
            fieldWithPath("data.pkValue").type(NUMBER).description("면접 질문 pkValue"),
            fieldWithPath("data.korTitleValue").type(STRING).description("면접 질문 제목 (한국어)"),
            fieldWithPath("data.categories.[].pkValue").type(NUMBER).description("카테고리 pkValue"),
            fieldWithPath("data.categories.[].titleValue").type(STRING).description("카테고리 이름"),
            fieldWithPath("data.voices.[].pkValue").type(NUMBER).description("카테고리 pkValue"),
            fieldWithPath("data.voices.[].questionPkValue").type(NUMBER).description("면접 질문 pk 값"),
            fieldWithPath("data.voices.[].genderValue").type(STRING).description("면접 질문 음성 성별 값"),
            fieldWithPath("data.voices.[].fileUrlValue").type(STRING).description("면접 질문 음성 file url 값"),
        )

    @DisplayName("면접 질문 단 건 조회")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .log().all()
                .filter(
                    document(
                        "interview-question",
                        pathParameters(parameterWithName("pkValue").description("면접 질문 pkValue")),
                        responseFields(responseFields),
                    ),
                )
                .`when`()
                .get("/api/v0/interview/questions/{pkValue}", 1)

        response.statusCode shouldBe 200
    }
}
