package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponseV2
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.application.port.usecase.InterviewScriptServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.InterviewScriptDbPort
import org.idd.dia.domain.ConflictException
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class InterviewScriptService(
    private val interviewScriptDbPort: InterviewScriptDbPort,
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
) : InterviewScriptServiceUseCase {
    override fun create(
        request: InterviewScriptCreateRequest,
        requestMemberPk: Member.Pk,
    ): InterviewScript.Pk {
        val questionEntity = interviewQuestionDbPort.getEntityWithRelations(request.getQuestionPk())
        synchronized(this) {
            val scriptAlreadyExists =
                interviewScriptDbPort.isExists(
                    questionEntity = questionEntity,
                    ownerPk = requestMemberPk,
                )
            if (scriptAlreadyExists) {
                throw ConflictException("이미 스크립트가 존재합니다")
            }
            val newScriptEntity =
                InterviewScriptEntity.new(
                    ownerPk = requestMemberPk,
                    questionEntity = questionEntity,
                    content = request.getContent(),
                    time = LocalDateTime.now(),
                )
            val saved = interviewScriptDbPort.save(newScriptEntity)
            return saved.getPk()
        }
    }

    override fun read(
        questionPk: InterviewQuestion.Pk,
        requestMemberPk: Member.Pk,
        readTime: LocalDateTime,
    ): InterviewScriptResponseV2 {
        val questionEntity = interviewQuestionDbPort.getEntityWithRelations(questionPk)
        val scriptEntity =
            interviewScriptDbPort.getEntity(
                questionEntity = questionEntity,
                ownerPk = requestMemberPk,
            )
        scriptEntity.read(readTime)

        return InterviewScriptResponseV2.from(scriptEntity)
    }

    // todo: 캐시 추가
    override fun getScript(
        scriptPk: InterviewScript.Pk,
        requestMemberPk: Member.Pk,
    ): InterviewScriptResponseV2 {
        val scriptEntity: InterviewScriptEntity =
            interviewScriptDbPort.getEntity(scriptPk)

        return InterviewScriptResponseV2.from(scriptEntity)
    }

    override fun updateContent(
        scriptPk: InterviewScript.Pk,
        request: InterviewScriptUpdateRequest,
        requestMemberPk: Member.Pk,
        updateTime: LocalDateTime,
    ): InterviewScriptResponseV2 {
        val entity = interviewScriptDbPort.getEntity(scriptPk)
        entity.updateContent(
            newContent = request.getContent(),
            updateTime = updateTime,
        )
        return InterviewScriptResponseV2.from(entity)
    }
}
