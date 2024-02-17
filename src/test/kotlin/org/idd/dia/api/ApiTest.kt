package org.idd.dia.api

import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType.BOOLEAN
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class ApiTest {
    lateinit var spec: RequestSpecification

    @LocalServerPort
    private var serverPort: Int = 0

    @Autowired
    private val apiTestDataHandler: ApiTestDataHandler? = null

    final val commonResponseFields =
        listOf(
            fieldWithPath("status").type(NUMBER).description("응답 상태"),
            fieldWithPath("detail").type(STRING).optional().description("디테일"),
        )

    final val pageResponseFields =
        listOf(
            fieldWithPath("data.pageSize").type(NUMBER).description("페이지 사이즈"),
            fieldWithPath("data.pageNumber").type(NUMBER).description("페이지 번호"),
            fieldWithPath("data.totalPages").type(NUMBER).description("전체 페이지"),
        )

    final val scrollResponseFields =
        listOf(
            fieldWithPath("data.next").type(BOOLEAN).description("다음 페이지 유무"),
        )

    val gson = Gson()

    @BeforeEach
    fun beforeEach(restDocumentation: RestDocumentationContextProvider) {
        RestAssured.port = serverPort
        apiTestDataHandler!!.setUp()
        spec =
            RequestSpecBuilder()
                .addFilters(
                    listOf(
                        RequestLoggingFilter(),
                        ResponseLoggingFilter(),
                        documentationConfiguration(restDocumentation)
                            .operationPreprocessors()
                            .withRequestDefaults(Preprocessors.prettyPrint())
                            .withResponseDefaults(Preprocessors.prettyPrint()),
                    ),
                )
                .build()
    }

    @AfterEach
    fun afterEach() {
        apiTestDataHandler!!.truncate()
    }

    protected fun RequestSpecification.defaultAuthorization(): RequestSpecification  {
        return this.authorization("1")
    }

    protected fun RequestSpecification.authorization(token: String): RequestSpecification = this.header("authorization", token)
}
