package org.idd.dia.application.service

import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.application.port.`in`.InterviewPracticeServiceUseCase
import org.idd.dia.application.port.out.InterviewQuestionDbPort
import org.idd.dia.application.port.out.InterviewQuestionVoiceDbPort
import org.idd.dia.domain.InterviewQuestion
import org.springframework.stereotype.Service

@Service
class InterviewPracticeService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionVoiceDbPort: InterviewQuestionVoiceDbPort
) : InterviewPracticeServiceUseCase {

    override fun getSinglePractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse {
        val interviewQuestion = interviewQuestionDbPort.getInterviewQuestion(interviewQuestionPk)
        val interviewQuestionVoices = interviewQuestionVoiceDbPort.getVoicesOfSingleQuestion(interviewQuestionPk)
        return SingleInterviewPracticeResponse(
            question = interviewQuestion,
            voices = interviewQuestionVoices
        )
    }
}
