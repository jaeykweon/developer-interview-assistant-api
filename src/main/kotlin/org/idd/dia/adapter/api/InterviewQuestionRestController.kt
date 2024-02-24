package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.model.CustomPage
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
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
) {
    /** 문제 목록 조회 */
    @GetMapping("/interview/questions")
    fun getQuestions(
        @RequestAuth(required = false) memberPk: Member.Pk? = null,
        @RequestParam(required = false) categoryValues: Set<String>,
        @RequestParam(required = false) bookmark: Boolean? = null,
        pageable: Pageable,
    ): ApiResponse<CustomPage<InterviewQuestionResponse>> {
        val categories: Set<InterviewQuestionCategory.Title> =
            categoryValues.mapToSet {
                InterviewQuestionCategory.Title(it)
            }

        if (memberPk.isNotNull() && bookmark.isNull()) {
            val questionPage: CustomPage<InterviewQuestionResponse> =
                interviewQuestionServiceUseCase.getQuestionsOfMember(
                    memberPk,
                    categories,
                    pageable,
                ).toCustomPage()
            return ApiResponse.ok(questionPage)
        }

        if (memberPk.isNotNull() && bookmark.isTrue()) {
            val questionPage: CustomPage<InterviewQuestionResponse> =
                interviewQuestionServiceUseCase.getBookmarkedQuestionsOfMember(
                    memberPk,
                    categories,
                    pageable,
                ).toCustomPage()

            return ApiResponse.ok(questionPage)
        }

        val questionPage: CustomPage<InterviewQuestionResponse> =
            interviewQuestionServiceUseCase.getQuestionPageOfGuest(
                categories,
                pageable,
            ).toCustomPage()
        return ApiResponse.ok(questionPage)
    }

    /** 문제 단 건 조회 */
    @GetMapping("/interview/questions/{questionPkValue}")
    fun getQuestion(
        @RequestAuth(required = false) memberPk: Member.Pk? = null,
        @PathVariable questionPkValue: Long,
    ): ApiResponse<InterviewQuestionResponse> {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        val interviewQuestion =
            interviewQuestionServiceUseCase
                .getQuestion(memberPk, questionPk)
        return ApiResponse.ok(interviewQuestion)
    }

    @PostMapping("/interview/questions/{questionPkValue}/bookmark")
    fun setQuestionBookmark(
        @PathVariable questionPkValue: Long,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<Long> {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        return ApiResponse.ok(
            interviewQuestionServiceUseCase.bookmarkQuestion(memberPk, questionPk),
        )
    }

    @DeleteMapping("/interview/questions/{questionPkValue}/bookmark")
    fun deleteQuestionBookmark(
        @PathVariable questionPkValue: Long,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<Long> {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        return ApiResponse.ok(
            interviewQuestionServiceUseCase.deleteQuestionBookmark(memberPk, questionPk),
        )
    }
}
