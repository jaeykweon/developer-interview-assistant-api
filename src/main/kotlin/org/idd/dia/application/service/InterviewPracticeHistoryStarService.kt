package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewPracticeHistoryStarResultResponse
import org.idd.dia.application.port.usecase.InterviewPracticeHistoryStarServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewPracticeHistoryDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewPracticeHistoryStarService(
    private val memberDbPort: MemberDbPort,
    private val interviewPracticeHistoryDbPort: InterviewPracticeHistoryDbPort,
) : InterviewPracticeHistoryStarServiceUseCase {
    override fun setStarOfInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
        star: Boolean,
    ): InterviewPracticeHistoryStarResultResponse {
        val memberEntity = memberDbPort.getEntity(pk = memberPk)
        val practiceHistoryEntity: InterviewPracticeHistoryEntity =
            interviewPracticeHistoryDbPort.getSingleEntity(interviewPracticeHistoryPk, memberEntity)

        if (star) {
            practiceHistoryEntity.star()
        } else {
            practiceHistoryEntity.unStar()
        }

        return InterviewPracticeHistoryStarResultResponse.fromEntity(practiceHistoryEntity)
    }
}
