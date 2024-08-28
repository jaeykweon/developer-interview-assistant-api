package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionCategoryDbPort
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionCategoryMappingDbPort
import org.idd.dia.application.service.internal.InterviewQuestionInternalService
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.getPk
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.domain.model.Member
import org.idd.dia.util.isNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewQuestionService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionCategoryDbPort: InterviewQuestionCategoryDbPort,
    private val interviewQuestionCategoryMappingDbPort: InterviewQuestionCategoryMappingDbPort,
    private val memberDbPort: MemberDbPort,
    private val interviewQuestionInternalService: InterviewQuestionInternalService,
) : InterviewQuestionServiceUseCase {
    override fun register(request: RegisterInterviewQuestionRequest): InterviewQuestion.Pk {
        val categoryEntities: Set<InterviewQuestionCategoryEntity> =
            interviewQuestionCategoryDbPort
                .getEntities(request.getCategoryPks())
        val questionEntity: InterviewQuestionEntity =
            interviewQuestionDbPort.save(
                InterviewQuestionEntity.new(
                    title = request.getTitle(),
                ),
            )

        interviewQuestionCategoryMappingDbPort.overwriteQuestionCategories(
            interviewQuestionEntity = questionEntity,
            interviewQuestionCategoryEntities = categoryEntities,
        )

        return questionEntity.getPk()
    }

    override fun getQuestionsForGuest(
        categories: Set<InterviewQuestionCategory.Title>?,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        if (categories.isNullOrEmpty()) {
            return interviewQuestionInternalService.getPage(pageable)
        }

        val categoryEntities = interviewQuestionCategoryDbPort.getEntities(categories)
        return interviewQuestionInternalService.getPage(
            categoryEntities,
            pageable,
        )
    }

    override fun getQuestionsForMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>?,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val memberEntity: MemberEntity =
            memberDbPort.getEntity(pk = memberPk)

        if (categories.isNullOrEmpty()) {
            return interviewQuestionInternalService.getPage(
                memberEntity,
                pageable,
            )
        }

        val categoryEntities = interviewQuestionCategoryDbPort.getEntities(categories)

        return interviewQuestionInternalService.getPage(
            memberEntity,
            categoryEntities,
            pageable,
        )
    }

    override fun getBookmarkedQuestions(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>?,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val memberEntity: MemberEntity =
            memberDbPort.getEntity(pk = memberPk)

        if (categories.isNullOrEmpty()) {
            return interviewQuestionInternalService.getBookmarkedPage(
                memberEntity,
                pageable,
            )
        }

        val categoryEntities: Set<InterviewQuestionCategoryEntity> =
            interviewQuestionCategoryDbPort.getEntities(categories)

        return interviewQuestionInternalService.getBookmarkedPage(
            memberEntity,
            categoryEntities,
            pageable,
        )
    }

    override fun getQuestion(
        memberPk: Member.Pk?,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionResponse {
        val questionEntity = interviewQuestionDbPort.getEntityWithRelations(pk = questionPk)

        if (memberPk.isNull()) {
            return interviewQuestionInternalService.getSingleResponse(questionEntity)
        }

        val memberEntity = memberDbPort.getEntity(pk = memberPk)
        return interviewQuestionInternalService.getSingleResponse(
            questionEntity,
            memberEntity,
        )
    }
}
