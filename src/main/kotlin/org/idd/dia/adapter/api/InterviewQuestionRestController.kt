package org.idd.dia.adapter.api

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.model.CustomPage
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.util.mapToSet
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

/**
 * 면접 질문 관련 API
 */
@ApiV0RestController
class InterviewQuestionRestController(
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase,
) {
    /** 문제 목록 조회 */
    @GetMapping("/interview/questions")
    fun getQuestions(
        @RequestParam(required = false) categoryValues: Set<String>,
        @PageableDefault pageable: Pageable,
    ): ApiResponse<CustomPage<InterviewQuestionResponse>> {
        val categories: Set<InterviewQuestionCategory.Title> =
            categoryValues.mapToSet {
                InterviewQuestionCategory.Title(it)
            }
        val questionPage: CustomPage<InterviewQuestionResponse> =
            interviewQuestionServiceUseCase
                .getQuestionPage(
                    categories,
                    pageable,
                ).toCustomPage()
        return ApiResponse.ok(questionPage)
    }

    /** 문제 단 건 조회 */
    @GetMapping("/interview/questions/{questionPkValue}")
    fun getQuestion(
        @PathVariable questionPkValue: Long,
    ): ApiResponse<InterviewQuestionResponse> {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        val interviewQuestion =
            interviewQuestionServiceUseCase
                .getQuestion(questionPk)
        return ApiResponse.ok(interviewQuestion)
    }
}
