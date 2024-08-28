package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponseV2
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import java.time.LocalDateTime

interface InterviewScriptServiceUseCase {
    fun create(
        request: InterviewScriptCreateRequest,
        requestMemberPk: Member.Pk,
    ): InterviewScript.Pk

    fun read(
        questionPk: InterviewQuestion.Pk,
        requestMemberPk: Member.Pk,
        readTime: LocalDateTime,
    ): InterviewScriptResponseV2

    fun updateContent(
        scriptPk: InterviewScript.Pk,
        request: InterviewScriptUpdateRequest,
        requestMemberPk: Member.Pk,
        updateTime: LocalDateTime,
    ): InterviewScriptResponseV2

    fun getScript(
        scriptPk: InterviewScript.Pk,
        requestMemberPk: Member.Pk,
    ): InterviewScriptResponseV2
}
