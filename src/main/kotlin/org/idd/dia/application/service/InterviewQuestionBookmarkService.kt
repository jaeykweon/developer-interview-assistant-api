package org.idd.dia.application.service

import org.idd.dia.application.port.usecase.InterviewQuestionBookmarkServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionBookmarkMappingDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
class InterviewQuestionBookmarkService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val memberDbPort: MemberDbPort,
    private val interviewQuestionBookmarkMappingDbPort: InterviewQuestionBookmarkMappingDbPort,
) : InterviewQuestionBookmarkServiceUseCase {
    override fun bookmarkQuestion(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): Long {
        val questionEntity = interviewQuestionDbPort.getWithOutRelations(pk = questionPk)
        val memberEntity = memberDbPort.getEntity(pk = memberPk)

        return interviewQuestionBookmarkMappingDbPort.addBookmark(
            memberEntity = memberEntity,
            questionEntity = questionEntity,
        )
    }

    override fun deleteQuestionBookmark(
        memberPk: Member.Pk,
        questionPk: InterviewQuestion.Pk,
    ): Long {
        val questionEntity = interviewQuestionDbPort.getWithOutRelations(pk = questionPk)
        val memberEntity = memberDbPort.getEntity(pk = memberPk)

        return interviewQuestionBookmarkMappingDbPort.removeBookmark(
            memberEntity = memberEntity,
            questionEntity = questionEntity,
        )
    }
}
