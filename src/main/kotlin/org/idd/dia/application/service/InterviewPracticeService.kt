package org.idd.dia.application.service

import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.adapter.db.repository.InterviewQuestionVoiceRepository
import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.application.port.`in`.InterviewPracticeServiceUseCase
import org.idd.dia.application.port.out.InterviewQuestionDbPort
import org.idd.dia.application.port.out.InterviewQuestionVoiceDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.util.mapToSet
import org.springframework.stereotype.Service
import java.io.PipedReader

@Service
class InterviewPracticeService(
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val interviewQuestionVoiceRepository: InterviewQuestionVoiceRepository
) : InterviewPracticeServiceUseCase {

    override fun getSinglePractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse {
        val interviewQuestionEntity = interviewQuestionRepository.get(interviewQuestionPk)
        val interviewQuestionVoiceEntities = interviewQuestionVoiceRepository.getVoicesOfSingleQuestion(interviewQuestionPk)
        return SingleInterviewPracticeResponse(
            question = interviewQuestion,
            voices = interviewQuestionVoices
        )
    }
}
