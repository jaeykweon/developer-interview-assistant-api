package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewQuestionBookmarkResponse
import org.idd.dia.application.dto.InterviewQuestionCollectionDetailViewModel
import org.idd.dia.application.dto.InterviewQuestionCollectionSimpleViewModel
import org.idd.dia.application.dto.InterviewQuestionCollectionSimpleViewModels
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usecase.InterviewQuestionBookmarkServiceUseCase
import org.idd.dia.application.port.usecase.InterviewQuestionCollectionServiceUseCase
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.model.CustomPage
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.domain.model.InterviewQuestionCollection
import org.idd.dia.domain.model.Member
import org.idd.dia.util.isNotNull
import org.idd.dia.util.isNull
import org.idd.dia.util.isTrue
import org.idd.dia.util.mapToSet
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * 면접 질문 관련 API
 */
@ApiV0RestController
class InterviewQuestionRestController(
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase,
    private val interviewQuestionBookmarkServiceUseCase: InterviewQuestionBookmarkServiceUseCase,
    private val interviewQuestionCollectionServiceUseCase: InterviewQuestionCollectionServiceUseCase,
) {
    /** 문제 목록 조회 */
    @GetMapping("/interview/questions")
    fun getQuestions(
        @RequestAuth(required = false) memberPk: Member.Pk? = null,
        @RequestParam(required = false) categoryValues: Set<String>? = null,
        @RequestParam(required = false) bookmark: Boolean? = null,
        pageable: Pageable,
    ): ApiResponse<CustomPage<InterviewQuestionResponse>> {
        val categories: Set<InterviewQuestionCategory.Title>? =
            categoryValues?.mapToSet {
                InterviewQuestionCategory.Title(it)
            }

        val questionPage =
            when {
                memberPk.isNull() && bookmark.isNull() ->
                    interviewQuestionServiceUseCase.getQuestionsForGuest(
                        categories,
                        pageable,
                    )
                memberPk.isNotNull() && bookmark.isNull() ->
                    interviewQuestionServiceUseCase.getQuestionsForMember(
                        memberPk,
                        categories,
                        pageable,
                    )
                memberPk.isNotNull() && bookmark.isTrue() ->
                    interviewQuestionServiceUseCase.getBookmarkedQuestions(
                        memberPk,
                        categories,
                        pageable,
                    )
                else ->
                    interviewQuestionServiceUseCase.getQuestionsForGuest(
                        categories,
                        pageable,
                    )
            }.toCustomPage()

        return ApiResponse.ok(questionPage)
    }

    /** 문제 단 건 조회 */
    @GetMapping("/interview/questions/{questionPk}")
    fun getQuestion(
        @PathVariable questionPk: InterviewQuestion.Pk,
        @RequestAuth(required = false) memberPk: Member.Pk? = null,
    ): ApiResponse<InterviewQuestionResponse> {
        val interviewQuestion =
            interviewQuestionServiceUseCase
                .getQuestion(memberPk, questionPk)
        return ApiResponse.ok(interviewQuestion)
    }

    /**
     * 문제 북마크
     */
    @PostMapping("/interview/questions/{questionPk}/bookmark")
    fun postQuestionBookmark(
        @PathVariable questionPk: InterviewQuestion.Pk,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<InterviewQuestionBookmarkResponse> {
        return ApiResponse.ok(
            interviewQuestionBookmarkServiceUseCase.bookmarkQuestion(memberPk, questionPk),
        )
    }

    @DeleteMapping("/interview/questions/{questionPk}/bookmark")
    fun deleteQuestionBookmark(
        @PathVariable questionPk: InterviewQuestion.Pk,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<InterviewQuestionBookmarkResponse> {
        return ApiResponse.ok(
            interviewQuestionBookmarkServiceUseCase.deleteQuestionBookmark(memberPk, questionPk),
        )
    }

    // todo: pageable 적용
    @GetMapping("/interview/practice/collections")
    fun getPracticeCollections(
        @RequestAuth(required = false) memberPk: Member.Pk? = null,
        pageable: Pageable,
    ): ApiResponse<List<InterviewQuestionCollectionSimpleViewModel>> {
        val collections: InterviewQuestionCollectionSimpleViewModels =
            interviewQuestionCollectionServiceUseCase.getInterviewQuestionCollections()
        return ApiResponse.ok(
            collections.values,
        )
    }

    @GetMapping("/interview/practice/collections/{collectionPk}")
    fun getPracticeCollection(
        @PathVariable collectionPk: InterviewQuestionCollection.Pk,
        @RequestAuth(required = false) memberPk: Member.Pk? = null,
    ): ApiResponse<InterviewQuestionCollectionDetailViewModel> {
        if (memberPk.isNull()) {
            val defaultCollection: InterviewQuestionCollectionDetailViewModel =
                interviewQuestionCollectionServiceUseCase
                    .getInterviewQuestionCollectionForGuest(
                        collectionPk,
                    )
            return ApiResponse.ok(defaultCollection)
        }

        val collectionOfMember =
            interviewQuestionCollectionServiceUseCase
                .getInterviewQuestionCollectionForMember(
                    memberPk,
                    collectionPk,
                )
        return ApiResponse.ok(collectionOfMember)
    }
}
