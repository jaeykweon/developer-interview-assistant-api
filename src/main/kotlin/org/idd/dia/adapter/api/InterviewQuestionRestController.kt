package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.domain.model.CustomPage
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.domain.model.Member
import org.idd.dia.util.isNull
import org.idd.dia.util.mapToSet
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
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
        pageable: Pageable,
    ): ApiResponse<CustomPage<InterviewQuestionResponse>> {
        val categories: Set<InterviewQuestionCategory.Title> =
            categoryValues.mapToSet {
                InterviewQuestionCategory.Title(it)
            }

        if (memberPk.isNull()) {
            val questionPage: CustomPage<InterviewQuestionResponse> =
                interviewQuestionServiceUseCase
                    .getQuestionPageOfGuest(
                        categories,
                        pageable,
                    ).toCustomPage()
            return ApiResponse.ok(questionPage)
        }

        val questionPage: CustomPage<InterviewQuestionResponse> =
            interviewQuestionServiceUseCase
                .getQuestionPageOfMember(
                    memberPk,
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

    @PutMapping("/interview/questions/{questionPkValue}/bookmark")
    fun setQuestionBookmark(
        @PathVariable questionPkValue: Long,
        @RequestAuth memberPk: Member.Pk,
        bookmark: Boolean,
    ): ApiResponse<InterviewQuestionResponse> {
        val questionPk = InterviewQuestion.Pk(questionPkValue)
        interviewQuestionServiceUseCase.toggleQuestionBookmarkAndReturnStatus(memberPk, questionPk, bookmark)
        return ApiResponse.ok(null)
    }
}
