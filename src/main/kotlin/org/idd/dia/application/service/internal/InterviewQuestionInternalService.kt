package org.idd.dia.application.service.internal

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionBookmarkMappingDbPort
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.findPkMatches
import org.idd.dia.domain.entity.getPk
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.model.InterviewQuestion
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
        memberEntity: MemberEntity,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        return this.getPage(
            memberEntity,
            categoryEntities = emptySet(),
            pageable = pageable,
        )
    }

    fun getPage(
        memberEntity: MemberEntity,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionPage: Page<InterviewQuestionEntity> =
            interviewQuestionDbPort.getPageWithRelations(categoryEntities, pageable)

        val questionBookmarkMappings: Iterable<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappings(
                memberEntity,
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
        memberEntity: MemberEntity,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionBookmarkMappings: Page<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappingsWithQuestion(
                ownerEntity = memberEntity,
                pageable = pageable,
            )

        return this.getBookmarkedPage(questionBookmarkMappings)
    }

    fun getBookmarkedPage(
        memberEntity: MemberEntity,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val questionBookmarkMappings: Page<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappingsWithQuestion(
                ownerEntity = memberEntity,
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
        memberEntity: MemberEntity,
    ): InterviewQuestionResponse {
        val interviewQuestionBookmarkMappingEntities: Set<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort
                .getMappings(
                    memberEntity,
                    questionEntity,
                ).toSet()

        return InterviewQuestionResponse.withCheckingBookmark(questionEntity, interviewQuestionBookmarkMappingEntities)
    }

    fun getQuestionsWithBookmark(
        memberEntity: MemberEntity,
        pks: Iterable<InterviewQuestion.Pk>,
    ): List<InterviewQuestionResponse> {
        val interviewQuestionEntities: List<InterviewQuestionEntity> =
            interviewQuestionDbPort.getEntitiesWithRelations(pks)

        val bookmarkMappings: List<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingDbPort.getMappings(memberEntity, interviewQuestionEntities)

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
