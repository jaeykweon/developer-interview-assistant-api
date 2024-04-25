package org.idd.dia.api

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

/**
 * @see org.idd.dia.adapter.api.InterviewPracticeRestController.deletePracticeHistory
 */
@DisplayName("면접 연습 히스토리 삭제 API")
class InterviewPracticeHistoryDeleteApiTest : ApiTest() {
    @DisplayName("성공 케이스")
    @Test
    fun success() {
        val response =
            given(this.spec)
                .log().all()
                .defaultAuthorization()
                .filter(
                    document(
                        "delete-interview-history",
                        requestHeaders(authRequired),
                    ),
                )
                .`when`()
                .delete("api/v0/interview/practice/histories/1001")

        response.statusCode() shouldBe 200
    }
}
