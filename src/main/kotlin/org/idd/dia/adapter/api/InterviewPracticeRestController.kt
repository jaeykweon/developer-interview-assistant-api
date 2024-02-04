package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.application.port.usecase.InterviewPracticeServiceUseCase
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@ApiV0RestController
class InterviewPracticeRestController(
    private val interviewPracticeService: InterviewPracticeServiceUseCase,
) {
    /** 연습 기록 저장 */
    @PostMapping("/interview/practice/history")
    fun recordPracticeHistory(
        @RequestAuth memberPk: Member.Pk,
        @RequestBody request: RecordInterviewPracticeRequest,
    ): ApiResponse<Long> {
        val savedPk = interviewPracticeService.recordInterviewPractice(memberPk, request)
        return ApiResponse.ok(savedPk.value)
    }

    /** 연습 기록 목록 조회 */
    @GetMapping("/interview/practice/histories")
    fun getPracticeHistories(
        @RequestAuth memberPk: Member.Pk,
        @RequestParam previousPkValue: Long? = null,
    ): ApiResponse<CustomScroll<InterviewPracticeHistoryResponse>> {
        val previousPk =
            previousPkValue?.let { InterviewPracticeHistory.Pk(previousPkValue) }
                ?: InterviewPracticeHistory.Pk.max()
        return ApiResponse.ok(
            interviewPracticeService.getInterviewPracticeHistories(memberPk, previousPk),
        )
    }

    /** 연습 기록 단 건 조회 */
    @GetMapping("/interview/practice/histories/{practiceHistoryPkValue}")
    fun getPracticeHistory(
        @RequestAuth memberPk: Member.Pk,
        @PathVariable practiceHistoryPkValue: Long,
    ): ApiResponse<InterviewPracticeHistoryResponse> {
        val practiceHistoryPk = InterviewPracticeHistory.Pk(practiceHistoryPkValue)
        return ApiResponse.ok(
            interviewPracticeService.getInterviewPracticeHistory(memberPk, practiceHistoryPk),
        )
    }

    /** 연습 기록 삭제 */
    @DeleteMapping("/interview/practice/histories/{practiceHistoryPkValue}")
    fun deletePracticeHistory(
        @RequestAuth memberPk: Member.Pk,
        @PathVariable practiceHistoryPkValue: Long,
    ): ApiResponse<Long> {
        interviewPracticeService.deleteInterviewPracticeHistory(
            memberPk,
            InterviewPracticeHistory.Pk(practiceHistoryPkValue),
        )
        return ApiResponse.ok(practiceHistoryPkValue)
    }
}
