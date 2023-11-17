package org.idd.dia.application.port.`in`

import org.idd.dia.application.dto.InterviewQuestionVoicesOfSingleQuestion
import org.idd.dia.domain.InterviewQuestion

interface InterviewQuestionVoiceServiceUseCase {
    fun getVoicesOfSingleQuestion(interviewQuestionPk: InterviewQuestion.Pk): InterviewQuestionVoicesOfSingleQuestion
}
