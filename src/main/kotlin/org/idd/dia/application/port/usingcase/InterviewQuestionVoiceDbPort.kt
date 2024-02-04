package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewQuestionVoiceEntity

interface InterviewQuestionVoiceDbPort {
    fun getVoicesOfSingleQuestion(questionEntity: InterviewQuestionEntity): Set<InterviewQuestionVoiceEntity>
}
