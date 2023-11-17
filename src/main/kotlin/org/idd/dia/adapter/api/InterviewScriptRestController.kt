package org.idd.dia.adapter.api

import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.application.port.`in`.InterviewScriptServiceUseCase
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.idd.dia.domain.Member
import org.idd.dia.domain.UnAuthorizedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@ApiV0RestController
class InterviewScriptRestController(
    private val interviewScriptServiceUseCase: InterviewScriptServiceUseCase
) {
    @PostMapping("/interview/scripts")
    fun create(
        @RequestBody interviewScriptCreateRequest: InterviewScriptCreateRequest,
        @RequestHeader("Authorization") token: String? = null
    ): InterviewScript {
        require(token != null) { throw UnAuthorizedException() }
        val requestMemberPk = Member.Pk(token.toLong())
        return interviewScriptServiceUseCase.create(interviewScriptCreateRequest, requestMemberPk)
    }

    @GetMapping("/interview/scripts")
    fun get(
        @RequestParam questionPk: Long,
        @RequestHeader("Authorization") token: String? = null
    ): InterviewScript {
        require(token != null) { throw UnAuthorizedException() }
        val time = LocalDateTime.now()
        val requestMemberPkValue = token.toLong()
        return interviewScriptServiceUseCase.read(
            questionPk = InterviewQuestion.Pk(questionPk),
            requestMemberPk = Member.Pk(requestMemberPkValue),
            readTime = time,
        )
    }

    @PatchMapping("/interview/scripts/{scriptPk}")
    fun updateContent(
        @PathVariable scriptPk: Long,
        @RequestBody request: InterviewScriptUpdateRequest,
        @RequestHeader("Authorization") token: String? = null
    ): InterviewScript {
        require(token != null) { throw UnAuthorizedException() }
        val requestMemberPkValue = token.toLong()
        val time = LocalDateTime.now()
        return interviewScriptServiceUseCase.updateContent(
            scriptPk = InterviewScript.Pk(scriptPk),
            request = request,
            requestMemberPk = Member.Pk(requestMemberPkValue),
            updateTime = time
        )
    }
}
