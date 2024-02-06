package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.MemberRepository
import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.application.port.usecase.InterviewPracticeServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewPracticeHistoryDbPort
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class InterviewPracticeService(
    private val interviewPracticeHistoryDbPort: InterviewPracticeHistoryDbPort,
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val memberRepository: MemberRepository,
) : InterviewPracticeServiceUseCase {
    override fun recordInterviewPractice(
        memberPk: Member.Pk,
        request: RecordInterviewPracticeRequest,
    ): InterviewPracticeHistory.Pk {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val questionEntity = interviewQuestionDbPort.getByPk(pk = request.getInterviewQuestionPk())
        val newRecordEntity =
            InterviewPracticeHistoryEntity(
                pk = InterviewPracticeHistory.Pk.new(),
                owner = memberEntity,
                question = questionEntity,
                content = request.getContent(),
                type = request.getType(),
                elapsedTime = request.getElapsedTime(),
                filePath = request.getFilePathOrNull(),
                createdTime = LocalDateTime.now(),
            )
        val saved = interviewPracticeHistoryDbPort.save(newRecordEntity)
        return saved.getPk()
    }

    override fun getInterviewPracticeHistories(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk,
    ): CustomScroll<InterviewPracticeHistoryResponse> {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val entityScroll: CustomScroll<InterviewPracticeHistoryEntity> =
            interviewPracticeHistoryDbPort.getScrollAfterPk(
                memberEntity = memberEntity,
                pk = previousPk,
            )
        return entityScroll.map { InterviewPracticeHistoryResponse(it) }
    }

    override fun getInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistoryResponse {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val entity = interviewPracticeHistoryDbPort.getByPk(interviewPracticeHistoryPk)
        return InterviewPracticeHistoryResponse(entity)
    }

    override fun deleteInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ) {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        interviewPracticeHistoryDbPort.deleteByPk(interviewPracticeHistoryPk)
    }
}
