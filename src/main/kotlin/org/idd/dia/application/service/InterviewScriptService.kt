package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewScriptCreateRequest
import org.idd.dia.application.dto.InterviewScriptResponse
import org.idd.dia.application.dto.InterviewScriptResponseV2
import org.idd.dia.application.dto.InterviewScriptUpdateRequest
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.application.port.usecase.InterviewScriptServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.InterviewScriptDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.ConflictException
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.getPk
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class InterviewScriptService(
    private val interviewScriptDbPort: InterviewScriptDbPort,
    private val memberDbPort: MemberDbPort,
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase,
) : InterviewScriptServiceUseCase {
    override fun createOrThrowIfExist(
        request: InterviewScriptCreateRequest,
        requestMemberPk: Member.Pk,
    ): InterviewScript.Pk {
        val questionEntity = interviewQuestionDbPort.getEntityWithCategoriesAndVoices(request.getQuestionPk())
        val ownerEntity = memberDbPort.getEntity(requestMemberPk)
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
                ownerEntity = ownerEntity,
                questionEntity = questionEntity,
                content = request.getContent(),
                createdTime = LocalDateTime.now(),
                lastReadTime = LocalDateTime.now(),
                lastModifiedTime = LocalDateTime.now(),
            )
        val saved = interviewScriptDbPort.save(newScriptEntity)
        return saved.getPk()
    }

    override fun read(
        questionPk: InterviewQuestion.Pk,
        requestMemberPk: Member.Pk,
        readTime: LocalDateTime,
    ): InterviewScriptResponse {
        val questionEntity = interviewQuestionDbPort.getEntityWithCategoriesAndVoices(questionPk)
        val ownerEntity = memberDbPort.getEntity(requestMemberPk)
        val scriptEntity =
            interviewScriptDbPort.getByPkAndOwnerPk(
                questionEntity = questionEntity,
                ownerEntity = ownerEntity,
            )
        scriptEntity.read(readTime)

        val questionResponse =
            interviewQuestionServiceUseCase.getQuestionWithBookmark(
                memberPk = requestMemberPk,
                pk = scriptEntity.question.getPk(),
            )
        return InterviewScriptResponse.of(scriptEntity, questionResponse)
    }

    // todo: 캐시 추가?
    override fun getScript(
        scriptPk: InterviewScript.Pk,
        requestMemberPk: Member.Pk,
    ): InterviewScriptResponse {
        val scriptEntity: InterviewScriptEntity =
            interviewScriptDbPort.getByPk(scriptPk)
        val questionResponse =
            interviewQuestionServiceUseCase.getQuestionWithBookmark(
                memberPk = requestMemberPk,
                pk = scriptEntity.question.getPk(),
            )

        return InterviewScriptResponse.of(scriptEntity, questionResponse)
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

    override fun getScripts(
        questionPks: Collection<InterviewQuestion.Pk>,
        memberPk: Member.Pk,
    ): List<InterviewScriptResponseV2> {
        val ownerEntity: MemberEntity =
            memberDbPort
                .getEntity(memberPk)
        val questionEntities =
            interviewQuestionDbPort
                .getEntitiesWithOutRelations(questionPks)
        val scriptEntities: List<InterviewScriptEntity> =
            interviewScriptDbPort
                .getAllByQuestionsOfMember(questionEntities, ownerEntity)

        return scriptEntities.map {
            InterviewScriptResponseV2.from(it)
        }
    }
}
