package org.idd.dia.application.service.internal

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionBookmarkMappingDbPort
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.findPkMatches
import org.idd.dia.domain.entity.getPk
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class InterviewQuestionInternalService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionBookmarkMappingDbPort: InterviewQuestionBookmarkMappingDbPort,
) {
    fun getPage(pageable: Pageable): Page<InterviewQuestionResponse> {
        val questionPage: Page<InterviewQuestionEntity> =
            interviewQuestionDbPort.getPageWithRelations(
                pageable,
            )
        return questionPage.map { questionEntity ->
            InterviewQuestionResponse.withoutCheckingBookmark(
                questionEntity,
            )
        }
    }

    fun getPage(
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionPage: Page<InterviewQuestionEntity> =
            interviewQuestionDbPort.getPageWithRelations(
                categoryEntities,
                pageable,
            )

        return questionPage.map { questionEntity ->
            InterviewQuestionResponse.withoutCheckingBookmark(
                questionEntity,
            )
        }
    }

    fun getPage(
        memberPk: Member.Pk,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        return this.getPage(
            memberPk,
            categoryEntities = emptySet(),
            pageable = pageable,
        )
    }

    fun getPage(
        memberPk: Member.Pk,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionPage: Page<InterviewQuestionEntity> =
            interviewQuestionDbPort.getPageWithRelations(categoryEntities, pageable)

        val questionBookmarkMappings: Iterable<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappings(
                memberPk,
                questionPage.content,
            )
        return questionPage.map { question ->
            InterviewQuestionResponse.withCheckingBookmark(
                question,
                questionBookmarkMappings,
            )
        }
    }

    fun getBookmarkedPage(
        memberPk: Member.Pk,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionBookmarkMappings: Page<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappingsWithQuestion(
                memberPk = memberPk,
                pageable = pageable,
            )

        return this.getBookmarkedPage(questionBookmarkMappings)
    }

    fun getBookmarkedPage(
        memberPk: Member.Pk,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionBookmarkMappings: Page<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappingsWithQuestion(
                memberPk = memberPk,
                categoryEntities = categoryEntities,
                pageable = pageable,
            )

        return this.getBookmarkedPage(questionBookmarkMappings)
    }

    private fun getBookmarkedPage(
        interviewQuestionBookmarkMappingEntityPage: Page<InterviewQuestionBookmarkMappingEntity>,
    ): Page<InterviewQuestionResponse> {
        val questionEntities =
            interviewQuestionDbPort.getEntitiesWithRelations(
                pks = interviewQuestionBookmarkMappingEntityPage.map { it.question.getPk() },
            )

        return interviewQuestionBookmarkMappingEntityPage.map { mapping ->
            val questionEntity = questionEntities.findPkMatches(mapping.question.getPk())!!
            InterviewQuestionResponse.withCheckingBookmark(
                questionEntity,
                setOf(mapping),
            )
        }
    }

    fun getSingleResponse(questionEntity: InterviewQuestionEntity): InterviewQuestionResponse {
        return InterviewQuestionResponse.withoutCheckingBookmark(questionEntity)
    }

    fun getSingleResponse(
        questionEntity: InterviewQuestionEntity,
        memberPk: Member.Pk,
    ): InterviewQuestionResponse {
        val interviewQuestionBookmarkMappingEntities: Set<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort
                .getMappings(
                    memberPk,
                    questionEntity,
                ).toSet()

        return InterviewQuestionResponse.withCheckingBookmark(questionEntity, interviewQuestionBookmarkMappingEntities)
    }

    fun getQuestionsWithBookmark(
        memberPk: Member.Pk,
        pks: Iterable<InterviewQuestion.Pk>,
    ): List<InterviewQuestionResponse> {
        val interviewQuestionEntities: List<InterviewQuestionEntity> =
            interviewQuestionDbPort.getEntitiesWithRelations(pks)

        val bookmarkMappings: List<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappings(memberPk, interviewQuestionEntities)

        return interviewQuestionEntities.map {
            InterviewQuestionResponse.withCheckingBookmark(
                it,
                bookmarkMappings,
            )
        }
    }

    fun getQuestionsWithoutBookmark(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionResponse> {
        val interviewQuestionEntities = interviewQuestionDbPort.getEntitiesWithRelations(pks)
        return interviewQuestionEntities.map {
            InterviewQuestionResponse.withoutCheckingBookmark(it)
        }
    }
}
