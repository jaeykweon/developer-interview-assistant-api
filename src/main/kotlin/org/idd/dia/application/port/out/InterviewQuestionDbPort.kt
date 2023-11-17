package org.idd.dia.application.port.out

import org.idd.dia.domain.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionDbPort {
    fun save(question: InterviewQuestion): InterviewQuestion
    fun getInterviewQuestion(pk: InterviewQuestion.Pk): InterviewQuestion
    fun getInterviewQuestions(pageable: Pageable): Page<InterviewQuestion>
}
