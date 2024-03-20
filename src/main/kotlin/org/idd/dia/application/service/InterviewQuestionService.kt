package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionCategoryDbPort
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionBookmarkMappingDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionCategoryMappingDbPort
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.findPkMatches
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
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
    private val interviewQuestionBookmarkMappingDbPort: InterviewQuestionBookmarkMappingDbPort,
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

    override fun getQuestionsOfGuest(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val categoryEntities = interviewQuestionCategoryDbPort.getEntities(categories)

        val pageDataWithRelations: Page<InterviewQuestionEntity> =
            interviewQuestionDbPort.getPageWithCategories(
                categoryEntities,
                pageable,
            )
        return pageDataWithRelations.map { questionEntity ->
            InterviewQuestionResponse.withoutCheckingBookmark(
                questionEntity,
            )
        }
    }

    override fun getQuestionsOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val memberEntity = memberDbPort.getEntity(pk = memberPk)

        val categoryEntities = interviewQuestionCategoryDbPort.getEntities(categories)

        val pageDataWithRelations: Page<InterviewQuestionEntity> =
            interviewQuestionDbPort.getPageWithCategoriesAndVoices(categoryEntities, pageable)

        val interviewQuestionBookmarkMappingEntities: Iterable<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappings(
                memberEntity,
                pageDataWithRelations.content,
            )
        return pageDataWithRelations.map { questionEntity ->
            InterviewQuestionResponse.withCheckingBookmark(
                questionEntity,
                interviewQuestionBookmarkMappingEntities,
            )
        }
    }

    override fun getBookmarkedQuestionsOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val memberEntity = memberDbPort.getEntity(pk = memberPk)
        val categoryEntities = interviewQuestionCategoryDbPort.getEntities(categories)

        val interviewQuestionBookmarkMappingEntityPage: Page<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappingsWithQuestion(
                ownerEntity = memberEntity,
                categoryEntities = categoryEntities,
                pageable = pageable,
            )

        val questionEntities =
            interviewQuestionDbPort.getEntitiesWithCategoriesAndVoices(
                pks = interviewQuestionBookmarkMappingEntityPage.map { it.getQuestionPk() },
            )

        return interviewQuestionBookmarkMappingEntityPage.map { mapping ->
            val questionEntity = questionEntities.findPkMatches(mapping.getQuestionPk())!!
            InterviewQuestionResponse.withCheckingBookmark(
                questionEntity,
                setOf(mapping),
            )
        }
    }

    override fun getQuestion(
        memberPk: Member.Pk?,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionResponse {
        val questionEntity = interviewQuestionDbPort.getEntityWithCategoriesAndVoices(pk = questionPk)

        if (memberPk.isNull()) {
            return InterviewQuestionResponse.withoutCheckingBookmark(questionEntity)
        }

        val memberEntity = memberDbPort.getEntity(pk = memberPk)
        val interviewQuestionBookmarkMappingEntities: Set<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort
                .getMappings(
                    memberEntity,
                    questionEntity,
                ).toSet()

        return InterviewQuestionResponse.withCheckingBookmark(questionEntity, interviewQuestionBookmarkMappingEntities)
    }

    override fun getQuestionsWithBookmark(
        ownerEntity: MemberEntity,
        pks: Iterable<InterviewQuestion.Pk>,
    ): List<InterviewQuestionResponse> {
        val interviewQuestionEntities = interviewQuestionDbPort.getEntitiesWithCategoriesAndVoices(pks)
        val bookmarkMappings = interviewQuestionBookmarkMappingDbPort.getMappings(ownerEntity, interviewQuestionEntities)
        return interviewQuestionEntities.map {
            InterviewQuestionResponse.withCheckingBookmark(
                it,
                bookmarkMappings,
            )
        }
    }

    override fun getQuestionsWithoutBookmark(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionResponse> {
        val interviewQuestionEntities = interviewQuestionDbPort.getEntitiesWithCategoriesAndVoices(pks)
        return interviewQuestionEntities.map {
            InterviewQuestionResponse.withoutCheckingBookmark(it)
        }
    }
}
