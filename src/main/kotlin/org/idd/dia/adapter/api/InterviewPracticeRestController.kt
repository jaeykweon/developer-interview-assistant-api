package org.idd.dia.adapter.api

import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.application.port.`in`.InterviewPracticeServiceUseCase
import org.idd.dia.domain.InterviewQuestion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@ApiV0RestController
class InterviewPracticeRestController(
    private val interviewPracticeService: InterviewPracticeServiceUseCase
) {

    @GetMapping("/interview/practice/single")
    fun singleInterviewPractice(
        @RequestParam interviewQuestionPkValue: Long
    ): SingleInterviewPracticeResponse {
        val interviewQuestionPk = InterviewQuestion.Pk(interviewQuestionPkValue)
        return interviewPracticeService.getSinglePractice(interviewQuestionPk)
    }
}
