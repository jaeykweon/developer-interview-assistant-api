package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponse
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

@ApiV0RestController
class InterviewScriptRestController(
    private val interviewScriptServiceUseCase: InterviewScriptServiceUseCase,
) {
    @PostMapping("/interview/scripts")
    fun create(
        @RequestAuth memberPk: Member.Pk,
        @RequestBody interviewScriptCreateRequest: InterviewScriptCreateRequest,
    ): ApiResponse<InterviewScriptResponse> {
        val interviewScriptResponse = interviewScriptServiceUseCase.create(interviewScriptCreateRequest, memberPk)
        return ApiResponse.ok(interviewScriptResponse)
    }

    @GetMapping("/interview/scripts")
    fun get(
        @RequestAuth memberPk: Member.Pk,
        @RequestParam questionPk: Long,
    ): ApiResponse<InterviewScriptResponse> {
        val time = LocalDateTime.now()
        val interviewScriptResponse =  interviewScriptServiceUseCase.read(
            questionPk = InterviewQuestion.Pk(questionPk),
            requestMemberPk = memberPk,
            readTime = time,
        )
        return ApiResponse.ok(interviewScriptResponse)
    }

    @PatchMapping("/interview/scripts/{scriptPk}")
    fun updateContent(
        @RequestAuth memberPk: Member.Pk,
        @PathVariable scriptPk: Long,
        @RequestBody request: InterviewScriptUpdateRequest,
    ): ApiResponse<InterviewScriptResponse> {
        val time = LocalDateTime.now()
        val interviewScriptResponse = interviewScriptServiceUseCase.updateContent(
            scriptPk = InterviewScript.Pk(scriptPk),
            request = request,
            requestMemberPk = memberPk,
            updateTime = time,
        )
        return ApiResponse.ok(interviewScriptResponse)
    }
}
