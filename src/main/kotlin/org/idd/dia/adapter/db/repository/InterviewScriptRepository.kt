package org.idd.dia.adapter.db.repository

import org.idd.dia.application.port.usingcase.InterviewScriptDbPort
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.InterviewScript
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InterviewScriptRepository(
    private val interviewScriptJpaRepository: InterviewScriptJpaRepository,
) : InterviewScriptDbPort {
    override fun isExists(
        questionEntity: InterviewQuestionEntity,
        ownerEntity: MemberEntity,
    ): Boolean {
        return interviewScriptJpaRepository
            .existsByQuestionAndOwner(
                question = questionEntity,
                owner = ownerEntity,
            )
    }

    override fun save(interviewScriptEntity: InterviewScriptEntity): InterviewScriptEntity {
        return interviewScriptJpaRepository.save(interviewScriptEntity)
    }

    override fun getByPk(pk: InterviewScript.Pk): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByIdOrNull(pk.value)
            ?: throw IllegalArgumentException()
    }

    override fun getByQuestionPk(questionEntity: InterviewQuestionEntity): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByQuestion(questionEntity) ?: throw IllegalArgumentException()
    }

    override fun getByPkAndOwnerPk(
        questionEntity: InterviewQuestionEntity,
        ownerEntity: MemberEntity,
    ): InterviewScriptEntity {
        return interviewScriptJpaRepository
            .findByQuestionAndOwner(
                question = questionEntity,
                owner = ownerEntity,
            ) ?: throw IllegalArgumentException()
    }
}

interface InterviewScriptJpaRepository : JpaRepository<InterviewScriptEntity, Long> {
    fun existsByQuestionAndOwner(
        question: InterviewQuestionEntity,
        owner: MemberEntity,
    ): Boolean

    fun findByQuestion(questionEntity: InterviewQuestionEntity): InterviewScriptEntity?

    fun findByQuestionAndOwner(
        question: InterviewQuestionEntity,
        owner: MemberEntity,
    ): InterviewScriptEntity?
}
