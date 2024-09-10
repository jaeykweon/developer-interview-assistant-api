package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface InterviewPracticeHistoryDbPort {
    fun save(entity: InterviewPracticeHistoryEntity): InterviewPracticeHistoryEntity

    fun getScroll(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionEntity: InterviewQuestionEntity?,
        star: Boolean?,
        pageable: Pageable,
    ): Slice<InterviewPracticeHistoryEntity>

    fun getSingleEntity(
        pk: InterviewPracticeHistory.Pk,
        memberPk: Member.Pk,
    ): InterviewPracticeHistoryEntity
}
