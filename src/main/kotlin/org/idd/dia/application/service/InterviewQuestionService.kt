package org.idd.dia.application.service

import org.idd.dia.application.port.`in`.InterviewQuestionServiceUseCase
import org.idd.dia.application.port.out.InterviewQuestionDbPort
import org.idd.dia.domain.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class InterviewQuestionService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort
) : InterviewQuestionServiceUseCase {

    override fun register(question: InterviewQuestion): InterviewQuestion {
        return interviewQuestionDbPort.save(question)
    }

    override fun getQuestions(pageable: Pageable): Page<InterviewQuestion> {
        return interviewQuestionDbPort.getInterviewQuestions(pageable)
    }

    override fun getQuestion(questionPk: InterviewQuestion.Pk): InterviewQuestion {
        return interviewQuestionDbPort.getInterviewQuestion(pk = questionPk)
    }
}
