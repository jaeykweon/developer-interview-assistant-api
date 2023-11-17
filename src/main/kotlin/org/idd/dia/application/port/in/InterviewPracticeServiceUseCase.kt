package org.idd.dia.application.port.`in`

import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.domain.InterviewQuestion

interface InterviewPracticeServiceUseCase {
    fun getSinglePractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse
}
