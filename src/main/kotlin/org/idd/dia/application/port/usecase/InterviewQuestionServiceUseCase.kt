package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.RegisterInterviewQuestionRequest
import org.idd.dia.application.dto.SetCategoriesOfInterviewQuestionRequest
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

    fun setCategoriesOfQuestion(
        questionPk: InterviewQuestion.Pk,
        setCategoriesOfInterviewQuestionRequest: SetCategoriesOfInterviewQuestionRequest,
    )

    fun toggleQuestionBookmarkAndReturnStatus(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
        bookmark: Boolean,
    ): Boolean

    fun getQuestionPageOfGuest(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse>

    fun getQuestionPageOfMember(
        memberPk: Member.Pk,
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionResponse>
}
