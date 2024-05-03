package org.idd.dia.adapter.db.repository

import org.idd.dia.application.port.usingcase.InterviewQuestionVoiceDbPort
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
class InterviewQuestionVoiceRepository(
    private val interviewQuestionVoiceJpaRepository: InterviewQuestionVoiceJpaRepository,
) : InterviewQuestionVoiceDbPort {
    override fun getVoicesOfSingleQuestion(questionEntity: InterviewQuestionEntity): Set<InterviewQuestionVoiceEntity> =
        interviewQuestionVoiceJpaRepository
            .findAllByQuestion(questionEntity)
}

interface InterviewQuestionVoiceJpaRepository : JpaRepository<InterviewQuestionVoiceEntity, Long> {
    fun findAllByQuestion(question: InterviewQuestionEntity): Set<InterviewQuestionVoiceEntity>
}
