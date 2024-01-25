package org.idd.dia.application.port.usingcase

import org.idd.dia.application.dto.SingleInterviewPracticeResponse
import org.idd.dia.domain.model.InterviewQuestion

interface InterviewPracticeDbPort {
    fun getSingleInterviewPractice(interviewQuestionPk: InterviewQuestion.Pk): SingleInterviewPracticeResponse
}
