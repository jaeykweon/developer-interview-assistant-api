package org.idd.dia.adapter.api

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.model.CustomPage
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@ApiV0RestController
class InterviewQuestionRestController(
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase,
) {

    @GetMapping("/interview/questions")
    fun getQuestions(
        @RequestParam previousPk: Long?,
        @PageableDefault pageable: Pageable,
    ): ApiResponse<CustomPage<InterviewQuestionResponse>> {
        val questionPage: CustomPage<InterviewQuestionResponse> =
            interviewQuestionServiceUseCase
                .getQuestionPage(
                    previousPk?.let { InterviewQuestion.Pk(it) },
                    pageable,
                ).toCustomPage()
        return ApiResponse.ok(questionPage)
    }

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
