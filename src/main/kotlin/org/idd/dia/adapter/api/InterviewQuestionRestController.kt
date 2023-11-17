package org.idd.dia.adapter.api

import org.idd.dia.application.port.`in`.InterviewQuestionServiceUseCase
import org.idd.dia.domain.InterviewQuestion
import org.springframework.data.domain.Page
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
    ): Page<InterviewQuestion> {
        return interviewQuestionServiceUseCase
            .getQuestions(pageable)
    }

    @GetMapping("/interview/questions/{questionPkValue}")
    fun get(
        @PathVariable questionPkValue: Long
    ): InterviewQuestion {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        return interviewQuestionServiceUseCase
            .getQuestion(questionPk)
    }
}
