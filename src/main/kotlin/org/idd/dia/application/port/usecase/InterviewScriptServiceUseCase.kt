package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponse
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import java.time.LocalDateTime

interface InterviewScriptServiceUseCase {
    fun createOrThrowIfExist(
        request: InterviewScriptCreateRequest,
        requestMemberPk: Member.Pk,
    ): InterviewScript.Pk

    fun read(
        questionPk: InterviewQuestion.Pk,
        requestMemberPk: Member.Pk,
        readTime: LocalDateTime,
    ): InterviewScriptResponse

    fun updateContent(
        scriptPk: InterviewScript.Pk,
        request: InterviewScriptUpdateRequest,
        requestMemberPk: Member.Pk,
        updateTime: LocalDateTime,
    ): InterviewScriptResponse

    fun getScript(
        scriptPk: InterviewScript.Pk,
        requestMemberPk: Member.Pk,
    ): InterviewScriptResponse
}
