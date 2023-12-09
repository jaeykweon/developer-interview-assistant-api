package org.idd.dia.adapter.db.repository

import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InterviewScriptRepository(
    private val interviewScriptJpaRepository: InterviewScriptJpaRepository
) {
    fun isExists(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): Boolean {
        return interviewScriptJpaRepository
            .existsByQuestionPkAndOwnerPk(
                questionPk = questionPk.value,
                ownerPk = ownerPk.value
            )
    }

    fun save(entity: InterviewScriptEntity): InterviewScriptEntity {
        return interviewScriptJpaRepository.save(entity)
    }

    fun get(pk: InterviewScript.Pk): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByIdOrNull(pk.value)
            ?: throw IllegalArgumentException()
    }

    fun get(questionPk: InterviewQuestion.Pk): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByQuestionPk(questionPk.value) ?: throw IllegalArgumentException()
    }

    fun getByQuestionPkAndOwnerPk(
        questionPk: InterviewQuestion.Pk,
        ownerPk: Member.Pk
    ): InterviewScriptEntity {
        return interviewScriptJpaRepository
            .findByQuestionPkAndOwnerPk(
                questionPk = questionPk.value,
                ownerPk = ownerPk.value
            ) ?: throw IllegalArgumentException()
    }
}

interface InterviewScriptJpaRepository : JpaRepository<InterviewScriptEntity, Long> {
    fun existsByQuestionPkAndOwnerPk(questionPk: Long, ownerPk: Long): Boolean
    fun findByQuestionPk(questionPk: Long): InterviewScriptEntity?
    fun findByQuestionPkAndOwnerPk(questionPk: Long, ownerPk: Long): InterviewScriptEntity?
}
