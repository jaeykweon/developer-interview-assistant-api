package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.application.dto.SetCategoriesOfInterviewQuestionRequest
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionServiceUseCase {
    fun register(request: RegisterInterviewQuestionRequest): InterviewQuestion.Pk

    fun getQuestionPage(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse>

    fun getQuestion(questionPk: InterviewQuestion.Pk): InterviewQuestionResponse

    fun setCategoriesOfQuestion(
        questionPk: InterviewQuestion.Pk,
        setCategoriesOfInterviewQuestionRequest: SetCategoriesOfInterviewQuestionRequest,
    )
}
