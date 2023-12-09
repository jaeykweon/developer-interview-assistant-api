package org.idd.dia.application.port.out

import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionVoice

interface InterviewQuestionVoiceDbPort {
    fun getVoicesOfSingleQuestion(interviewQuestion: InterviewQuestion.Pk): Set<InterviewQuestionVoice>
}
