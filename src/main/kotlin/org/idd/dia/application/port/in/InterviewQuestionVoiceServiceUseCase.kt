package org.idd.dia.application.port.`in`

import org.idd.dia.application.dto.InterviewQuestionVoicesOfSingleQuestion
import org.idd.dia.domain.model.InterviewQuestion

interface InterviewQuestionVoiceServiceUseCase {
    fun getVoicesOfSingleQuestion(interviewQuestionPk: InterviewQuestion.Pk): InterviewQuestionVoicesOfSingleQuestion
}
