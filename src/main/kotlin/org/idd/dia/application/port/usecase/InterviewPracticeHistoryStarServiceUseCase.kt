package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewPracticeHistoryStarResultResponse
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member

interface InterviewPracticeHistoryStarServiceUseCase {
    fun setStarOfInterviewPracticeHistory(
        memberPk: Member.Pk,
        interviewPracticeHistoryPk: InterviewPracticeHistory.Pk,
        star: Boolean,
    ): InterviewPracticeHistoryStarResultResponse
}
