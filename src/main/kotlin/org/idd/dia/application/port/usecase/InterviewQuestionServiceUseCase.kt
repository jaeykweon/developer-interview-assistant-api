package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionServiceUseCase {
    fun register(request: RegisterInterviewQuestionRequest): InterviewQuestion.Pk

    fun getQuestion(
        memberPk: Member.Pk?,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionResponse

    fun getQuestionsOfGuest(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse>

    fun getQuestionsOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse>

    /** 문제들 중 북마크 된 문제들만 조회 */
    fun getOnlyBookmarkedQuestionsOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse>

    fun getQuestionsWithBookmark(
        memberPk: Member.Pk,
        pks: Iterable<InterviewQuestion.Pk>,
    ): List<InterviewQuestionResponse>

    fun getQuestionsWithoutBookmark(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionResponse>

    fun getQuestionWithBookmark(
        memberPk: Member.Pk,
        pk: InterviewQuestion.Pk,
    ): InterviewQuestionResponse
}
