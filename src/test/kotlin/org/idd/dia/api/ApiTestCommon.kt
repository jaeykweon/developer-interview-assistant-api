package org.idd.dia.api

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
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
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class ApiTestCommon {
    lateinit var spec: RequestSpecification

    @LocalServerPort
    private var serverPort: Int = 0

    @Autowired
    private val apiTestDataHandler: ApiTestDataHandler? = null

    @BeforeEach
    fun beforeEach(restDocumentation: RestDocumentationContextProvider) {
        println("beforeEach")
        RestAssured.port = serverPort
        apiTestDataHandler!!.setUp()
        spec =
            RequestSpecBuilder()
                .addFilter(
                    RestAssuredRestDocumentation.documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(Preprocessors.prettyPrint())
                        .withResponseDefaults(Preprocessors.prettyPrint()),
                )
                .build()
    }

    @AfterEach
    fun afterEach() {
        apiTestDataHandler!!.truncate()
    }
}
