package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.InterviewQuestionCategoryRepository
import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.adapter.db.repository.mapping.InterviewQuestionCategoryMappingRepository
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.application.dto.SetCategoriesOfInterviewQuestionRequest
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewQuestionService(
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val interviewQuestionCategoryRepository: InterviewQuestionCategoryRepository,
    private val interviewQuestionCategoryMappingRepository: InterviewQuestionCategoryMappingRepository,
) : InterviewQuestionServiceUseCase {

    override fun register(request: RegisterInterviewQuestionRequest): InterviewQuestion.Pk {
        val categories: Set<InterviewQuestionCategoryEntity> =
            interviewQuestionCategoryRepository
                .getAllByPks(request.getCategoryPks())
        val questionEntity =
            interviewQuestionRepository.save(
                InterviewQuestionEntity(
                    pk = InterviewQuestion.Pk(),
                    korTitle = request.getTitle(),
                    categories = setOf(),
                    voices = setOf(),
                ),
            )

        interviewQuestionCategoryMappingRepository.overwriteQuestionCategories(
            interviewQuestionEntity = questionEntity,
            interviewQuestionCategoryEntities = categories,
        )

        return questionEntity.getPk()
    }

    override fun getQuestionPage(
        previousPk: InterviewQuestion.Pk?,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val cleanedPreviousPk = previousPk ?: InterviewQuestion.Pk.max()
        return interviewQuestionRepository
            .getPageWithRelations(cleanedPreviousPk, pageable)
            .map { InterviewQuestionResponse.from(it) }
    }

    override fun getQuestion(questionPk: InterviewQuestion.Pk): InterviewQuestionResponse {
        val questionEntity = interviewQuestionRepository.getByPk(pk = questionPk)
        return InterviewQuestionResponse.from(questionEntity)
    }

    override fun setCategoriesOfQuestion(
        questionPk: InterviewQuestion.Pk,
        setCategoriesOfInterviewQuestionRequest: SetCategoriesOfInterviewQuestionRequest,
    ) {
        val questionEntity = interviewQuestionRepository.getByPk(pk = questionPk)
        val categoryEntities = interviewQuestionCategoryRepository.getAllByPks(setCategoriesOfInterviewQuestionRequest.getCategoryPks())

        interviewQuestionCategoryMappingRepository.overwriteQuestionCategories(
            interviewQuestionEntity = questionEntity,
            interviewQuestionCategoryEntities = categoryEntities,
        )
    }
}
