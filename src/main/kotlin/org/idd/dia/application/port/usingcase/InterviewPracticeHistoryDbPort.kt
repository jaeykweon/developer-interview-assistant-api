package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory

interface InterviewPracticeHistoryDbPort {
    fun save(entity: InterviewPracticeHistoryEntity): InterviewPracticeHistoryEntity

    fun getScrollAfterPk(
        memberEntity: MemberEntity,
        pk: InterviewPracticeHistory.Pk,
    ): CustomScroll<InterviewPracticeHistoryEntity>

    fun getSingleEntity(
        pk: InterviewPracticeHistory.Pk,
        ownerEntity: MemberEntity,
    ): InterviewPracticeHistoryEntity

    fun deleteSingleEntity(
        pk: InterviewPracticeHistory.Pk,
        ownerEntity: MemberEntity,
    ): InterviewPracticeHistory.Pk
}
