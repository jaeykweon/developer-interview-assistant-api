package org.idd.dia.adapter.db.repository

import org.idd.dia.adapter.db.entity.InterviewScriptEntity
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InterviewScriptRepository(
    private val interviewScriptJpaRepository: InterviewScriptJpaRepository
) {
    fun save(entity: InterviewScriptEntity): InterviewScriptEntity {
        return interviewScriptJpaRepository.save(entity)
    }

    fun get(scriptPk: InterviewScript.Pk): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByIdOrNull(scriptPk.value)
            ?: throw IllegalArgumentException()
    }

    fun get(questionPk: InterviewQuestion.Pk): InterviewScriptEntity {
        return interviewScriptJpaRepository.findByQuestionPk(questionPk.value)
    }
}

interface InterviewScriptJpaRepository : JpaRepository<InterviewScriptEntity, Long> {
    fun findByQuestionPk(questionPk: Long): InterviewScriptEntity
}
