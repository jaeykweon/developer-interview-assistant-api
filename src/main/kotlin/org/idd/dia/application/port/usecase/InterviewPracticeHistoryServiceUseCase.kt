package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface InterviewPracticeHistoryServiceUseCase {
    /** 면접 연습 기록하기 */
    fun registerHistory(
        memberPk: Member.Pk,
        request: RecordInterviewPracticeRequest,
    ): InterviewPracticeHistory.Pk

    /** 면접 연습 기록 목록 조회하기 */
    fun getHistories(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionPk: InterviewQuestion.Pk?,
        star: Boolean?,
        pageable: Pageable,
    ): Slice<InterviewPracticeHistoryResponse>

    /** 면접 연습 기록 단 건 조회하기 */
    fun getHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistoryResponse

    /** 면접 연습 기록 삭제하기 */
    fun deleteHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistory.Pk
}
