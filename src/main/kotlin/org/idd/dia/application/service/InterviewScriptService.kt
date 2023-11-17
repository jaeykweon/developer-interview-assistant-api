package org.idd.dia.application.service

import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.application.port.`in`.InterviewScriptServiceUseCase
import org.idd.dia.application.port.out.InterviewScriptDbPort
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.idd.dia.domain.Member
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InterviewScriptService(
    private val interviewScriptDbPort: InterviewScriptDbPort
) : InterviewScriptServiceUseCase {

    override fun create(
        request: InterviewScriptCreateRequest,
        requestMemberPk: Member.Pk
    ): InterviewScript {
        val scriptAlreadyExists = interviewScriptDbPort.isExists(
            questionPk = request.questionPk,
            ownerPk = requestMemberPk
        )
        if (scriptAlreadyExists) { throw IllegalArgumentException() }

        val new = InterviewScript(
            pk = InterviewScript.Pk(),
            ownerPk = requestMemberPk,
            questionPk = request.questionPk,
            content = request.content,
            createdTime = LocalDateTime.now(),
            lastReadTime = LocalDateTime.now(),
            lastModifiedTime = LocalDateTime.now()
        )
        return interviewScriptDbPort.save(new)
    }

    override fun read(
        questionPk: InterviewQuestion.Pk,
        requestMemberPk: Member.Pk,
        readTime: LocalDateTime
    ): InterviewScript {
        val script: InterviewScript =
            interviewScriptDbPort
                .get(
                    questionPk = questionPk,
                    ownerPk = requestMemberPk
                )
                .readContent(readTime = readTime)
        return interviewScriptDbPort.save(script)
    }

    override fun updateContent(
        scriptPk: InterviewScript.Pk,
        request: InterviewScriptUpdateRequest,
        requestMemberPk: Member.Pk,
        updateTime: LocalDateTime
    ): InterviewScript {
        val updated: InterviewScript =
            interviewScriptDbPort
                .get(pk = scriptPk)
                .updateContent(
                    newContent = request.content,
                    updateTime = updateTime
                )
        return interviewScriptDbPort.save(updated)
    }
}
