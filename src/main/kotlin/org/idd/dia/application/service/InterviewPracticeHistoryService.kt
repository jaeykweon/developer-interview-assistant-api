package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.application.port.usecase.InterviewPracticeHistoryServiceUseCase
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewPracticeHistoryDbPort
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.service.internal.InterviewQuestionInternalService
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.idd.dia.util.mapToSet
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class InterviewPracticeHistoryService(
    private val interviewPracticeHistoryDbPort: InterviewPracticeHistoryDbPort,
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase,
    private val interviewQuestionInternalService: InterviewQuestionInternalService,
) : InterviewPracticeHistoryServiceUseCase {
    override fun registerHistory(
        memberPk: Member.Pk,
        request: RecordInterviewPracticeRequest,
    ): InterviewPracticeHistory.Pk {
        val questionEntity = interviewQuestionDbPort.getEntityWithOutRelations(pk = request.getInterviewQuestionPk())
        val newHistoryEntity =
            InterviewPracticeHistoryEntity.new(
                memberPk = memberPk,
                questionEntity = questionEntity,
                content = request.getContent(),
                type = request.getType(),
                elapsedTime = request.getElapsedTime(),
                filePath = request.getFilePathOrNull(),
                createdTime = LocalDateTime.now(),
            )
        val saved = interviewPracticeHistoryDbPort.save(newHistoryEntity)
        return saved.getPk()
    }

    override fun getHistories(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionPk: InterviewQuestion.Pk?,
        star: Boolean?,
        pageable: Pageable,
    ): Slice<InterviewPracticeHistoryResponse> {
        val questionEntity: InterviewQuestionEntity? =
            interviewQuestionPk?.let { interviewQuestionDbPort.getEntityWithRelations(it) }

        val entitySlice: Slice<InterviewPracticeHistoryEntity> =
            interviewPracticeHistoryDbPort.getScroll(
                memberPk = memberPk,
                previousPk = previousPk,
                interviewQuestionEntity = questionEntity,
                star = star,
                pageable = pageable,
            )

        val questionResponses =
            interviewQuestionInternalService.getQuestionsWithBookmark(
                memberPk,
                pks = entitySlice.mapToSet { it.getQuestionPk() },
            )

        return entitySlice.map { entity ->
            val matching = questionResponses.find { entity.getQuestionPk().value == it.pkValue }!!
            InterviewPracticeHistoryResponse.of(entity, matching)
        }
    }

    override fun getHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistoryResponse {
        val entity: InterviewPracticeHistoryEntity =
            interviewPracticeHistoryDbPort.getSingleEntity(interviewPracticeHistoryPk, memberPk)

        val questionResponse =
            interviewQuestionServiceUseCase.getQuestion(
                memberPk = memberPk,
                questionPk = entity.getQuestionPk(),
            )

        return InterviewPracticeHistoryResponse.of(entity, questionResponse)
    }

    override fun deleteHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistory.Pk {
        val entity: InterviewPracticeHistoryEntity =
            interviewPracticeHistoryDbPort.getSingleEntity(interviewPracticeHistoryPk, memberPk)
        entity.delete(memberPk)
        return interviewPracticeHistoryDbPort.save(entity).getPk()
    }
}
