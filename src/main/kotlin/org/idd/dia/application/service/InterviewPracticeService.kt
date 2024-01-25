package org.idd.dia.application.service

import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.adapter.db.repository.InterviewQuestionVoiceRepository
import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.application.port.usecase.InterviewPracticeServiceUseCase
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.stereotype.Service

@Service
class InterviewPracticeService(
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val interviewQuestionVoiceRepository: InterviewQuestionVoiceRepository,
) : InterviewPracticeServiceUseCase {

    override fun getSinglePractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse {
        val interviewQuestionEntity: InterviewQuestionEntity =
            interviewQuestionRepository.getByPk(interviewQuestionPk)

        val interviewQuestionVoiceEntities: Set<InterviewQuestionVoiceEntity> =
            interviewQuestionVoiceRepository.getVoicesOfSingleQuestion(interviewQuestionEntity)

        return SingleInterviewPracticeResponse(
            questionEntity = interviewQuestionEntity,
            voiceEntities = interviewQuestionVoiceEntities,
        )
    }
}
