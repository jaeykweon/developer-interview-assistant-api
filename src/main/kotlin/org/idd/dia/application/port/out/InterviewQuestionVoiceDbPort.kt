package org.idd.dia.application.port.out

import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewQuestionVoice

interface InterviewQuestionVoiceDbPort {
    fun getVoicesOfSingleQuestion(interviewQuestion: InterviewQuestion.Pk): Set<InterviewQuestionVoice>
}
