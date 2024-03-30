package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewPracticeHistoryResponse
import org.idd.dia.application.dto.InterviewPracticeHistoryStarResultResponse
import org.idd.dia.application.dto.RecordInterviewPracticeRequest
import org.idd.dia.application.port.usecase.InterviewPracticeHistoryServiceUseCase
import org.idd.dia.application.port.usecase.InterviewPracticeHistoryStarServiceUseCase
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@ApiV0RestController
class InterviewPracticeRestController(
    private val interviewPracticeHistoryService: InterviewPracticeHistoryServiceUseCase,
    private val interviewPracticeHistoryStarServiceUseCase: InterviewPracticeHistoryStarServiceUseCase,
) {
    /** 연습 기록 저장 */
    @PostMapping("/interview/practice/histories")
    fun recordPracticeHistory(
        @RequestAuth memberPk: Member.Pk,
        @RequestBody request: RecordInterviewPracticeRequest,
    ): ApiResponse<Long> {
        val savedPk = interviewPracticeHistoryService.registerHistory(memberPk, request)
        return ApiResponse.ok(savedPk.value)
    }

    /** 연습 기록 목록 조회 */
    @GetMapping("/interview/practice/histories")
    fun getPracticeHistories(
        @RequestAuth memberPk: Member.Pk,
        @RequestParam previousPkValue: Long? = null,
        @RequestParam questionPkValue: Long? = null,
    ): ApiResponse<CustomScroll<InterviewPracticeHistoryResponse>> {
        val previousPk: InterviewPracticeHistory.Pk? =
            previousPkValue?.let { InterviewPracticeHistory.Pk(previousPkValue) }
        val questionPk: InterviewQuestion.Pk? =
            questionPkValue?.let { InterviewQuestion.Pk(questionPkValue) }

        val sliceData: Slice<InterviewPracticeHistoryResponse> =
            interviewPracticeHistoryService.getHistories(memberPk, previousPk, questionPk)

        return ApiResponse.ok(
            sliceData.toCustomScroll(),
        )
    }

    /** 연습 기록 단 건 조회 */
    @GetMapping("/interview/practice/histories/{practiceHistoryPk}")
    fun getPracticeHistory(
        @PathVariable practiceHistoryPk: InterviewPracticeHistory.Pk,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<InterviewPracticeHistoryResponse> {
        return ApiResponse.ok(
            interviewPracticeHistoryService.getHistory(memberPk, practiceHistoryPk),
        )
    }

    /** 연습 기록 삭제 */
    @DeleteMapping("/interview/practice/histories/{practiceHistoryPk}")
    fun deletePracticeHistory(
        @PathVariable practiceHistoryPk: InterviewPracticeHistory.Pk,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<Long> {
        val deletedPk =
            interviewPracticeHistoryService.deleteHistory(
                memberPk,
                practiceHistoryPk,
            )
        return ApiResponse.ok(deletedPk.value)
    }

    /** 연습 기록 별표 */
    @PostMapping("/interview/practice/histories/{practiceHistoryPk}/star")
    fun starPracticeHistory(
        @PathVariable practiceHistoryPk: InterviewPracticeHistory.Pk,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<InterviewPracticeHistoryStarResultResponse> {
        val result =
            interviewPracticeHistoryStarServiceUseCase.setStarOfInterviewPracticeHistory(
                memberPk,
                practiceHistoryPk,
                star = true,
            )
        return ApiResponse.ok(result)
    }

    /** 연습 기록 별표 해제 */
    @DeleteMapping("/interview/practice/histories/{practiceHistoryPk}/star")
    fun unStarPracticeHistory(
        @PathVariable practiceHistoryPk: InterviewPracticeHistory.Pk,
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<InterviewPracticeHistoryStarResultResponse> {
        val result =
            interviewPracticeHistoryStarServiceUseCase.setStarOfInterviewPracticeHistory(
                memberPk,
                practiceHistoryPk,
                star = false,
            )
        return ApiResponse.ok(result)
    }
}
