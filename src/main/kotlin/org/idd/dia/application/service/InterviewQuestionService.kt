package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.InterviewQuestionCategoryRepository
import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.adapter.db.repository.MemberRepository
import org.idd.dia.adapter.db.repository.mapping.InterviewQuestionBookmarkMappingRepository
import org.idd.dia.adapter.db.repository.mapping.InterviewQuestionCategoryMappingRepository
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.application.dto.SetCategoriesOfInterviewQuestionRequest
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.findPkMatches
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.domain.model.Member
import org.idd.dia.util.isNotNull
import org.idd.dia.util.isNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewQuestionService(
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val interviewQuestionCategoryRepository: InterviewQuestionCategoryRepository,
    private val interviewQuestionCategoryMappingRepository: InterviewQuestionCategoryMappingRepository,
    private val memberRepository: MemberRepository,
    private val interviewQuestionBookmarkMappingRepository: InterviewQuestionBookmarkMappingRepository,
) : InterviewQuestionServiceUseCase {
    override fun register(request: RegisterInterviewQuestionRequest): InterviewQuestion.Pk {
        val categoryEntities: Set<InterviewQuestionCategoryEntity> =
            interviewQuestionCategoryRepository
                .getEntities(request.getCategoryPks())
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
            interviewQuestionCategoryEntities = categoryEntities,
        )

        return questionEntity.getPk()
    }

    override fun getQuestions(
        memberPk: Member.Pk?,
        categories: Set<InterviewQuestionCategory.Title>,
        bookmark: Boolean?,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        if (memberPk.isNotNull()) {
            return this.getQuestionsOfMember(
                memberPk,
                categories,
                pageable,
            )
        }
        if (memberPk.isNotNull() && bookmark.isNotNull()) {
            return this.getBookmarkedQuestionsOfMember(
                memberPk,
                categories,
                pageable,
            )
        }
        return this.getQuestionPageOfGuest(categories, pageable)
    }

    private fun getQuestionsOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val memberEntity = memberRepository.getByPk(pk = memberPk)

        val pageDataWithRelations: Page<InterviewQuestionEntity> =
            interviewQuestionRepository.getPageWithRelations(categories, pageable)

        val interviewQuestionBookmarkMappingsEntity: Iterable<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingRepository.getMappings(
                memberEntity,
                pageDataWithRelations.content,
            )
        return pageDataWithRelations.map { questionEntity ->
            InterviewQuestionResponse.withCheckingBookmark(
                questionEntity,
                interviewQuestionBookmarkMappingsEntity,
            )
        }
    }

    private fun getBookmarkedQuestionsOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val categoryEntities = interviewQuestionCategoryRepository.getEntities(categories)

        val interviewQuestionBookmarkMappingEntityPage: Page<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingRepository.getMappingsWithQuestion(
                ownerEntity = memberEntity,
                categoryEntities = categoryEntities,
                pageable = pageable,
            )

        val questionEntities =
            interviewQuestionRepository.getQuestions(
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

    private fun getQuestionPageOfGuest(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse> {
        val categoryEntities = interviewQuestionCategoryRepository.getEntities(categories)

        val pageDataWithRelations: Page<InterviewQuestionEntity> =
            interviewQuestionRepository.getPageWithRelations(
                categoryEntities,
                pageable,
            )
        return pageDataWithRelations.map { questionEntity ->
            InterviewQuestionResponse.withoutCheckingBookmark(
                questionEntity,
            )
        }
    }

    override fun getQuestion(
        memberPk: Member.Pk?,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionResponse {
        val questionEntity = interviewQuestionRepository.getEntityWithRelations(pk = questionPk)

        if (memberPk.isNull()) {
            return InterviewQuestionResponse.withoutCheckingBookmark(questionEntity)
        }

        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val interviewQuestionBookmarkMappingEntities: Set<InterviewQuestionBookmarkMappingEntity> =
            interviewQuestionBookmarkMappingRepository
                .getMappings(
                    memberEntity,
                    questionEntity,
                ).toSet()

        return InterviewQuestionResponse.withCheckingBookmark(questionEntity, interviewQuestionBookmarkMappingEntities)
    }

    override fun setCategoriesOfQuestion(
        questionPk: InterviewQuestion.Pk,
        setCategoriesOfInterviewQuestionRequest: SetCategoriesOfInterviewQuestionRequest,
    ) {
        val questionEntity = interviewQuestionRepository.getEntityWithRelations(pk = questionPk)
        val categoryEntities = interviewQuestionCategoryRepository.getEntities(setCategoriesOfInterviewQuestionRequest.getCategoryPks())

        interviewQuestionCategoryMappingRepository.overwriteQuestionCategories(
            interviewQuestionEntity = questionEntity,
            interviewQuestionCategoryEntities = categoryEntities,
        )
    }

    override fun bookmarkQuestion(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): Long {
        val questionEntity = interviewQuestionRepository.getWithOutRelations(pk = questionPk)
        val memberEntity = memberRepository.getByPk(pk = memberPk)

        return interviewQuestionBookmarkMappingRepository.addBookmark(
            memberEntity = memberEntity,
            questionEntity = questionEntity,
        )
    }

    override fun deleteQuestionBookmark(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): Long {
        val questionEntity = interviewQuestionRepository.getWithOutRelations(pk = questionPk)
        val memberEntity = memberRepository.getByPk(pk = memberPk)

        return interviewQuestionBookmarkMappingRepository.removeBookmark(
            memberEntity = memberEntity,
            questionEntity = questionEntity,
        )
    }
}
