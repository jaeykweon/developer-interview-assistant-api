package org.idd.dia.application.port.out

import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.domain.model.InterviewQuestion

interface InterviewPracticeDbPort {
    fun getSingleInterviewPractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse
}
