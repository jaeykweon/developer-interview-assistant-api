package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.SetCategoriesOfInterviewQuestionRequest
import org.idd.dia.application.port.usecase.InterviewQuestionCategoryServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionCategoryDbPort
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionCategoryMappingDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewQuestionCategoryService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionCategoryDbPort: InterviewQuestionCategoryDbPort,
    private val interviewQuestionCategoryMappingDbPort: InterviewQuestionCategoryMappingDbPort,
) : InterviewQuestionCategoryServiceUseCase {
    override fun setCategoriesOfQuestion(
        questionPk: InterviewQuestion.Pk,
        setCategoriesOfInterviewQuestionRequest: SetCategoriesOfInterviewQuestionRequest,
    ) {
        val questionEntity = interviewQuestionDbPort.getEntityWithCategoriesAndVoices(pk = questionPk)
        val categoryEntities = interviewQuestionCategoryDbPort.getEntities(setCategoriesOfInterviewQuestionRequest.getCategoryPks())

        interviewQuestionCategoryMappingDbPort.overwriteQuestionCategories(
            interviewQuestionEntity = questionEntity,
            interviewQuestionCategoryEntities = categoryEntities,
        )
    }
}
