package org.idd.dia.application.service

import org.idd.dia.application.dto.InterviewQuestionBookmarkResponse
import org.idd.dia.application.port.usecase.InterviewQuestionBookmarkServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionBookmarkMappingDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
class InterviewQuestionBookmarkService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionBookmarkMappingDbPort: InterviewQuestionBookmarkMappingDbPort,
) : InterviewQuestionBookmarkServiceUseCase {
    override fun bookmarkQuestion(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionBookmarkResponse {
        val questionEntity = interviewQuestionDbPort.getEntityWithOutRelations(pk = questionPk)

        interviewQuestionBookmarkMappingDbPort.addBookmark(
            memberPk = memberPk,
            questionEntity = questionEntity,
        )
        return InterviewQuestionBookmarkResponse.bookmarked()
    }

    override fun deleteQuestionBookmark(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): InterviewQuestionBookmarkResponse {
        val questionEntity = interviewQuestionDbPort.getEntityWithOutRelations(pk = questionPk)

        interviewQuestionBookmarkMappingDbPort.removeBookmark(
            memberPk,
            questionEntity,
        )
        return InterviewQuestionBookmarkResponse.unBookmarked()
    }
}
