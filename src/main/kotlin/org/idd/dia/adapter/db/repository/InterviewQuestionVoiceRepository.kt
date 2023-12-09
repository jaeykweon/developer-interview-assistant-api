package org.idd.dia.adapter.db.repository

import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
class InterviewQuestionVoiceRepository(
    private val interviewQuestionVoiceJpaRepository: InterviewQuestionVoiceJpaRepository
) {
    fun getVoicesOfSingleQuestion(interviewQuestionPk: InterviewQuestion.Pk): Set<InterviewQuestionVoiceEntity> {
        return interviewQuestionVoiceJpaRepository
            .findAllByQuestionPk(interviewQuestionPk.value)
    }
}

interface InterviewQuestionVoiceJpaRepository : JpaRepository<InterviewQuestionVoiceEntity, Long> {
    fun findAllByQuestionPk(interviewQuestionPk: Long): Set<InterviewQuestionVoiceEntity>
}
