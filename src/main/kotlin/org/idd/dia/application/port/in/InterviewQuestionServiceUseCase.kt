package org.idd.dia.application.port.`in`

import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionServiceUseCase {
    fun register(question: InterviewQuestion): InterviewQuestion
    fun getQuestions(pageable: Pageable): Page<InterviewQuestion>
    fun getQuestion(questionPk: InterviewQuestion.Pk): InterviewQuestion
}
