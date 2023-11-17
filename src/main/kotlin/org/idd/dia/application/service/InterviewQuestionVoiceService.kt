package org.idd.dia.application.service

import org.idd.dia.application.dto.InterviewQuestionVoicesOfSingleQuestion
import org.idd.dia.application.port.`in`.InterviewQuestionVoiceServiceUseCase
import org.idd.dia.application.port.out.InterviewQuestionVoiceDbPort
import org.idd.dia.domain.InterviewQuestion
import org.springframework.stereotype.Service

@Service
class InterviewQuestionVoiceService(
    private val interviewQuestionVoiceDBPort: InterviewQuestionVoiceDbPort
) : InterviewQuestionVoiceServiceUseCase {

    // todo: 이렇게 분리 해야할까 아니면 practice로 한꺼번에 해도 될까
    override fun getVoicesOfSingleQuestion(
        interviewQuestionPk: InterviewQuestion.Pk
    ): InterviewQuestionVoicesOfSingleQuestion {
        val voices = interviewQuestionVoiceDBPort.getVoicesOfSingleQuestion(interviewQuestionPk)
        return InterviewQuestionVoicesOfSingleQuestion(voices)
    }
}
