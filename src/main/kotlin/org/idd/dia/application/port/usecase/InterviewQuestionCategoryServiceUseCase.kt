package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.SetCategoriesOfInterviewQuestionRequest
import org.idd.dia.domain.model.InterviewQuestion

interface InterviewQuestionCategoryServiceUseCase {
    fun setCategoriesOfQuestion(
        questionPk: InterviewQuestion.Pk,
        setCategoriesOfInterviewQuestionRequest: SetCategoriesOfInterviewQuestionRequest,
    )
}
