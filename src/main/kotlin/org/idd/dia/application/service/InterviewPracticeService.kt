package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.MemberRepository
import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.application.port.usecase.InterviewPracticeServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewPracticeHistoryDbPort
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.idd.dia.util.mapToSet
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class InterviewPracticeService(
    private val interviewPracticeHistoryDbPort: InterviewPracticeHistoryDbPort,
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val memberRepository: MemberRepository,
    private val interviewQuestionService: InterviewQuestionService,
) : InterviewPracticeServiceUseCase {
    override fun registerInterviewPractice(
        memberPk: Member.Pk,
        request: RecordInterviewPracticeRequest,
    ): InterviewPracticeHistory.Pk {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val questionEntity = interviewQuestionDbPort.getWithOutRelations(pk = request.getInterviewQuestionPk())
        val newHistoryEntity =
            InterviewPracticeHistoryEntity.new(
                owner = memberEntity,
                question = questionEntity,
                content = request.getContent(),
                type = request.getType(),
                elapsedTime = request.getElapsedTime(),
                filePath = request.getFilePathOrNull(),
                createdTime = LocalDateTime.now(),
            )
        val saved = interviewPracticeHistoryDbPort.save(newHistoryEntity)
        return saved.getPk()
    }

    override fun getInterviewPracticeHistories(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionPk: InterviewQuestion.Pk?,
    ): Slice<InterviewPracticeHistoryResponse> {
        val memberEntity: MemberEntity = memberRepository.getByPk(pk = memberPk)
        val questionEntity: InterviewQuestionEntity? =
            interviewQuestionPk?.let { interviewQuestionDbPort.getEntityWithRelations(it) }

        val entitySlice: Slice<InterviewPracticeHistoryEntity> =
            interviewPracticeHistoryDbPort.getScroll(
                memberEntity = memberEntity,
                previousPk = previousPk,
                interviewQuestionEntity = questionEntity,
            )

        val questionResponses =
            interviewQuestionService.getQuestionsWithBookmark(
                ownerEntity = memberEntity,
                pks = entitySlice.mapToSet { it.getQuestionPk() },
            )

        return entitySlice.map { entity ->
            val matching = questionResponses.find { entity.getQuestionPk().value == it.pkValue }!!
            InterviewPracticeHistoryResponse.of(entity, matching)
        }
    }

    override fun getInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistoryResponse {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        val entity: InterviewPracticeHistoryEntity =
            interviewPracticeHistoryDbPort.getSingleEntity(interviewPracticeHistoryPk, memberEntity)

        val questionResponse =
            interviewQuestionService.getQuestion(
                memberPk = memberPk,
                questionPk = entity.getQuestionPk(),
            )

        return InterviewPracticeHistoryResponse.of(entity, questionResponse)
    }

    override fun deleteInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistory.Pk {
        val memberEntity = memberRepository.getByPk(pk = memberPk)
        return interviewPracticeHistoryDbPort.deleteSingleEntity(interviewPracticeHistoryPk, memberEntity)
    }
}
