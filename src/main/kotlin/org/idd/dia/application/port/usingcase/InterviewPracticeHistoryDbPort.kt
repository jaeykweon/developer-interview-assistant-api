package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface InterviewPracticeHistoryDbPort {
    fun save(entity: InterviewPracticeHistoryEntity): InterviewPracticeHistoryEntity

    fun getScroll(
        memberEntity: MemberEntity,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionEntity: InterviewQuestionEntity?,
        star: Boolean?,
        pageable: Pageable,
    ): Slice<InterviewPracticeHistoryEntity>

    fun getSingleEntity(
        pk: InterviewPracticeHistory.Pk,
        ownerEntity: MemberEntity,
    ): InterviewPracticeHistoryEntity

    fun deleteSingleEntity(
        pk: InterviewPracticeHistory.Pk,
        ownerEntity: MemberEntity,
    ): InterviewPracticeHistory.Pk
}
