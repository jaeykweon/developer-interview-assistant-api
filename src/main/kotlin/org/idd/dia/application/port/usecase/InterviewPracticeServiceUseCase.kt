package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.domain.model.InterviewQuestion

interface InterviewPracticeServiceUseCase {
    fun getSinglePractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse
}
