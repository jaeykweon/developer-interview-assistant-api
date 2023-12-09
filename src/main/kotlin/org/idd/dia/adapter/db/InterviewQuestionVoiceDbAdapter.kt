package org.idd.dia.adapter.db

import org.idd.dia.adapter.db.mapper.InterviewQuestionVoiceMapper
import org.idd.dia.adapter.db.repository.InterviewQuestionVoiceRepository
import org.idd.dia.application.port.out.InterviewQuestionVoiceDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionVoice
import org.idd.dia.util.mapToSet
import org.springframework.stereotype.Component

@Component
class InterviewQuestionVoiceDbAdapter(
    private val interviewQuestionVoiceRepository: InterviewQuestionVoiceRepository,
    private val interviewQuestionVoiceMapper: InterviewQuestionVoiceMapper
) : InterviewQuestionVoiceDbPort {

    override fun getVoicesOfSingleQuestion(interviewQuestion: InterviewQuestion.Pk): Set<InterviewQuestionVoice> {
        return interviewQuestionVoiceRepository
            .getVoicesOfSingleQuestion(interviewQuestion)
            .mapToSet { interviewQuestionVoiceMapper.toDomainModel(it) }
    }
}
