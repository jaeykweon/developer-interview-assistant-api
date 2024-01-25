package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.InterviewPracticeHistoryRepository
import org.idd.dia.adapter.db.repository.MemberRepository
import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class InterviewPracticeHistoryService(
    private val interviewPracticeHistoryRepository: InterviewPracticeHistoryRepository,
    private val memberRepository: MemberRepository,
) {
    fun save(
        memberPk: Member.Pk,
        type: InterviewPracticeHistory.Type,
        elapsedTime: InterviewPracticeHistory.ElapsedTime,
        filePath: InterviewPracticeHistory.FilePath,
    ): InterviewPracticeHistoryResponse {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val newEntity =
            InterviewPracticeHistoryEntity(
                pk = InterviewPracticeHistory.Pk.new(),
                owner = memberEntity,
                type = type,
                elapsedTime = elapsedTime,
                filePath = filePath,
                createdTime = LocalDateTime.now(),
            )
        val savedEntity = interviewPracticeHistoryRepository.save(newEntity)
        return InterviewPracticeHistoryResponse(savedEntity)
    }

    fun getScroll(
        previousPk: InterviewPracticeHistory.Pk?,
        memberPk: Member.Pk,
    ): CustomScroll<InterviewPracticeHistoryResponse> {
        val cleanedPreviousPk: InterviewPracticeHistory.Pk =
            previousPk
                ?: InterviewPracticeHistory.Pk.max()
        val entityScroll =
            interviewPracticeHistoryRepository
                .getScrollAfterPk(
                    memberEntity = memberRepository.getByPk(pk = memberPk),
                    pk = cleanedPreviousPk,
                )
        return entityScroll.map { InterviewPracticeHistoryResponse(it) }
    }
}
