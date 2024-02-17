package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Slice

interface InterviewPracticeServiceUseCase {
    /** 면접 연습 기록하기 */
    fun registerInterviewPractice(
        memberPk: Member.Pk,
        request: RecordInterviewPracticeRequest,
    ): InterviewPracticeHistory.Pk

    /** 면접 연습 기록 조회하기 */
    fun getInterviewPracticeHistories(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionPk: InterviewQuestion.Pk?,
    ): Slice<InterviewPracticeHistoryResponse>

    fun getInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistoryResponse

    /** 면접 연습 기록 삭제하기 */
    fun deleteInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
    ): InterviewPracticeHistory.Pk
    /** 실전 면접 연습하기 */
}
