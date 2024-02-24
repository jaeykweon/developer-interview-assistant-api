package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.MemberRepository
import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponse
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
    private val memberRepository: MemberRepository,
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
) : InterviewScriptServiceUseCase {
    override fun createOrThrowIfExist(
        request: InterviewScriptCreateRequest,
        requestMemberPk: Member.Pk,
    ): InterviewScript.Pk {
        val questionEntity = interviewQuestionDbPort.getEntityWithRelations(request.getQuestionPk())
        val ownerEntity = memberRepository.getByPk(requestMemberPk)
        val scriptAlreadyExists =
            interviewScriptDbPort.isExists(
                questionEntity = questionEntity,
                ownerEntity = ownerEntity,
            )
        if (scriptAlreadyExists) {
            throw ConflictException("이미 스크립트가 존재합니다")
        }
        val newScriptEntity =
            InterviewScriptEntity(
                pk = InterviewScript.Pk(),
                owner = ownerEntity,
                question = questionEntity,
                content = request.getContent(),
                createdTime = LocalDateTime.now(),
                lastReadTime = LocalDateTime.now(),
                lastModifiedTime = LocalDateTime.now(),
            )
        val saved = interviewScriptDbPort.save(newScriptEntity)
        return saved.getPk()
    }

    override fun getScript(
        scriptPk: InterviewScript.Pk,
        requestMemberPk: Member.Pk,
    ): InterviewScriptResponse {
        val scriptEntity = interviewScriptDbPort.getByPk(scriptPk)
        return InterviewScriptResponse.from(scriptEntity)
    }

    override fun read(
        questionPk: InterviewQuestion.Pk,
        requestMemberPk: Member.Pk,
        readTime: LocalDateTime,
    ): InterviewScriptResponse {
        val questionEntity = interviewQuestionDbPort.getEntityWithRelations(questionPk)
        val ownerEntity = memberRepository.getByPk(requestMemberPk)
        val scriptEntity =
            interviewScriptDbPort.getByPkAndOwnerPk(
                questionEntity = questionEntity,
                ownerEntity = ownerEntity,
            )
        scriptEntity.read(readTime)
        return InterviewScriptResponse.from(scriptEntity)
    }

    override fun updateContent(
        scriptPk: InterviewScript.Pk,
        request: InterviewScriptUpdateRequest,
        requestMemberPk: Member.Pk,
        updateTime: LocalDateTime,
    ): InterviewScriptResponse {
        val entity = interviewScriptDbPort.getByPk(scriptPk)
        entity.updateContent(
            newContent = request.getContent(),
            updateTime = updateTime,
        )
        return InterviewScriptResponse.from(entity)
    }
}
