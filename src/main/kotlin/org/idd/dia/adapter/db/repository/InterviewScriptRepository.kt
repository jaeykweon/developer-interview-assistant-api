package org.idd.dia.adapter.db.repository

import org.idd.dia.application.port.usingcase.InterviewScriptDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InterviewScriptRepository(
    private val interviewScriptJpaRepository: InterviewScriptJpaRepository,
) : InterviewScriptDbPort {
    override fun isExists(
        questionEntity: InterviewQuestionEntity,
        ownerPk: Member.Pk,
    ): Boolean {
        return interviewScriptJpaRepository
            .existsByQuestionAndMemberPkValue(
                question = questionEntity,
                memberPkValue = ownerPk.value,
            )
    }

    override fun save(interviewScriptEntity: InterviewScriptEntity): InterviewScriptEntity {
        return interviewScriptJpaRepository.save(interviewScriptEntity)
    }

    override fun getEntity(pk: InterviewScript.Pk): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByIdOrNull(pk.value)
            ?: throw NotFoundException("스크립트가 존재하지 않습니다 (pk: ${pk.value})")
    }

    override fun getEntity(questionEntity: InterviewQuestionEntity): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByQuestion(questionEntity)
            ?: throw NotFoundException("스크립트가 존재하지 않습니다 (questionPk: ${questionEntity.pkValue})")
    }

    override fun getEntity(
        questionEntity: InterviewQuestionEntity,
        ownerPk: Member.Pk,
    ): InterviewScriptEntity {
        return interviewScriptJpaRepository
            .findByQuestionAndMemberPkValue(
                question = questionEntity,
                memberPkValue = ownerPk.value,
            ) ?: throw NotFoundException(
            "스크립트가 존재하지 않습니다 (questionPk: ${questionEntity.pkValue}, ownerPk: ${ownerPk.value})",
        )
    }

    override fun getEntities(
        questionEntities: List<InterviewQuestionEntity>,
        ownerPk: Member.Pk,
    ): List<InterviewScriptEntity> {
        return interviewScriptJpaRepository.findAllByQuestionInAndMemberPkValue(
            questionEntities = questionEntities,
            memberPkValue = ownerPk.value,
        )
    }
}

interface InterviewScriptJpaRepository : JpaRepository<InterviewScriptEntity, Long> {
    fun existsByQuestionAndMemberPkValue(
        question: InterviewQuestionEntity,
        memberPkValue: Long,
    ): Boolean

    fun findByQuestion(questionEntity: InterviewQuestionEntity): InterviewScriptEntity?

    fun findByQuestionAndMemberPkValue(
        question: InterviewQuestionEntity,
        memberPkValue: Long,
    ): InterviewScriptEntity?

    fun findAllByQuestionInAndMemberPkValue(
        questionEntities: List<InterviewQuestionEntity>,
        memberPkValue: Long,
    ): List<InterviewScriptEntity>
}
