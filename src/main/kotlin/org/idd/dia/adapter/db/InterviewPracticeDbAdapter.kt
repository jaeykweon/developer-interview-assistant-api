package org.idd.dia.adapter.db

import org.idd.dia.adapter.db.mapper.InterviewQuestionMapper
import org.idd.dia.adapter.db.mapper.InterviewQuestionVoiceMapper
import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.adapter.db.repository.InterviewQuestionVoiceRepository
import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.application.port.out.InterviewPracticeDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.util.mapToSet
import org.springframework.stereotype.Component

@Component
class InterviewPracticeDbAdapter(
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val interviewQuestionMapper: InterviewQuestionMapper,
    private val interviewQuestionVoiceRepository: InterviewQuestionVoiceRepository,
    private val interviewQuestionVoiceMapper: InterviewQuestionVoiceMapper
) : InterviewPracticeDbPort {

    // todo: 이렇게 해야할까 아니면 레포지토리와 매퍼를 따로 만들어야할까
    override fun getSingleInterviewPractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse {
        val interviewQuestionEntity = interviewQuestionRepository.get(interviewQuestionPk)
        val interviewQuestion = interviewQuestionMapper.toDomainModel(interviewQuestionEntity)
        val interviewQuestionVoiceEntities = interviewQuestionVoiceRepository.getVoicesOfSingleQuestion(interviewQuestionPk)
        val interviewQuestionVoices = interviewQuestionVoiceEntities.mapToSet { interviewQuestionVoiceMapper.toDomainModel(it) }
        return SingleInterviewPracticeResponse(
            question = interviewQuestion,
            voices = interviewQuestionVoices
        )
    }
}
