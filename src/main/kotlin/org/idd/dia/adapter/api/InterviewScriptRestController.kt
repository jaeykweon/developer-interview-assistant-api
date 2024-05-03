package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponseV2
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.application.port.usecase.InterviewScriptServiceUseCase
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

/**
 * 스크립트(대본) 관련 API
 */
@ApiV0RestController
class InterviewScriptRestController(
    private val interviewScriptServiceUseCase: InterviewScriptServiceUseCase,
) {
    /** 스크립트 작성 */
    @PostMapping("/interview/scripts")
    fun createScript(
        @RequestAuth memberPk: Member.Pk,
        @RequestBody interviewScriptCreateRequest: InterviewScriptCreateRequest,
    ): ApiResponse<InterviewScriptResponseV2> {
        val newPk: InterviewScript.Pk =
            interviewScriptServiceUseCase.create(
                interviewScriptCreateRequest,
                memberPk,
            )
        val newScript = interviewScriptServiceUseCase.getScript(newPk, memberPk)
        return ApiResponse.ok(newScript)
    }

    /** 스크립트 조회 */
    @GetMapping("/interview/scripts")
    fun getScript(
        @RequestAuth memberPk: Member.Pk,
        @RequestParam questionPkValue: Long,
    ): ApiResponse<InterviewScriptResponseV2> {
        val time = LocalDateTime.now()
        val interviewScriptResponse =
            interviewScriptServiceUseCase.read(
                questionPk = InterviewQuestion.Pk(questionPkValue),
                requestMemberPk = memberPk,
                readTime = time,
            )
        return ApiResponse.ok(interviewScriptResponse)
    }

    /** 스크립트 수정 */
    @PatchMapping("/interview/scripts/{scriptPk}")
    fun editScript(
        @RequestAuth memberPk: Member.Pk,
        @PathVariable scriptPk: InterviewScript.Pk,
        @RequestBody request: InterviewScriptUpdateRequest,
    ): ApiResponse<InterviewScriptResponseV2> {
        val time = LocalDateTime.now()
        val interviewScriptResponse =
            interviewScriptServiceUseCase.updateContent(
                scriptPk = scriptPk,
                request = request,
                requestMemberPk = memberPk,
                updateTime = time,
            )
        return ApiResponse.ok(interviewScriptResponse)
    }
}
