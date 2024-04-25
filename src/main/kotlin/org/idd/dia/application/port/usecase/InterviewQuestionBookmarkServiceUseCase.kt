package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewQuestionBookmarkResultResponse
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member

interface InterviewQuestionBookmarkServiceUseCase {
    fun bookmarkQuestion(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionBookmarkResultResponse

    fun deleteQuestionBookmark(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionBookmarkResultResponse
}
