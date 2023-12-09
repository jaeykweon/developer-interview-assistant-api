package org.idd.dia.adapter.api

import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.application.port.`in`.InterviewScriptServiceUseCase
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
    private val interviewScriptServiceUseCase: InterviewScriptServiceUseCase
) {
    @PostMapping("/interview/scripts")
    fun create(
        @RequestBody interviewScriptCreateRequest: InterviewScriptCreateRequest,
    ): InterviewScript {
        return interviewScriptServiceUseCase.create(interviewScriptCreateRequest, TODO())
    }

    @GetMapping("/interview/scripts")
    fun get(
        @RequestParam questionPk: Long,
    ): InterviewScript {
        val time = LocalDateTime.now()
        return interviewScriptServiceUseCase.read(
            questionPk = InterviewQuestion.Pk(questionPk),
            requestMemberPk = TODO(),
            readTime = time,
        )
    }

    @PatchMapping("/interview/scripts/{scriptPk}")
    fun updateContent(
        @PathVariable scriptPk: Long,
        @RequestBody request: InterviewScriptUpdateRequest,
    ): InterviewScript {
        val time = LocalDateTime.now()
        return interviewScriptServiceUseCase.updateContent(
            scriptPk = InterviewScript.Pk(scriptPk),
            request = request,
            requestMemberPk = Member.Pk(TODO()),
            updateTime = time
        )
    }
}
