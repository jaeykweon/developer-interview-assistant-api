package org.idd.dia.adapter.api

import org.idd.dia.application.port.`in`.InterviewQuestionServiceUseCase
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV0RestController
class InterviewQuestionRestController(
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase
) {

    @GetMapping("/interview/questions")
    fun getQuestions(
        @PageableDefault pageable: Pageable
    ): ApiResponse<CustomPage<InterviewQuestion>> {
        val questionPage = interviewQuestionServiceUseCase
            .getQuestions(pageable)
            .toCustomPage()
        return ApiResponse.ok(questionPage)
    }

    @GetMapping("/interview/questions/{questionPkValue}")
    fun get(
        @PathVariable questionPkValue: Long
    ): ApiResponse<InterviewQuestion> {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        val interviewQuestion = interviewQuestionServiceUseCase
            .getQuestion(questionPk)
        return ApiResponse.ok(interviewQuestion)
    }
}
