package org.idd.dia.adapter.db

import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.adapter.db.mapper.InterviewScriptMapper
import org.idd.dia.adapter.db.repository.InterviewScriptRepository
import org.idd.dia.application.port.out.InterviewScriptDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Component

@Component
class InterviewScriptDbAdapter(
    private val interviewScriptMapper: InterviewScriptMapper,
    private val interviewScriptRepository: InterviewScriptRepository
) : InterviewScriptDbPort {

    override fun isExists(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): Boolean {
        return interviewScriptRepository
            .isExists(
                questionPk = questionPk,
                ownerPk = ownerPk
            )
    }

    override fun save(interviewScript: InterviewScript): InterviewScript {
        val newEntity = interviewScriptMapper.toEntity(interviewScript)
        val savedEntity = interviewScriptRepository.save(newEntity)
        return interviewScriptMapper.toDomain(savedEntity)
    }

    override fun get(pk: InterviewScript.Pk): InterviewScript {
        val entity = interviewScriptRepository.get(pk = pk)
        return interviewScriptMapper.toDomain(entity)
    }

    override fun get(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): InterviewScript {
        val entity: InterviewScriptEntity =
            interviewScriptRepository
                .getByQuestionPkAndOwnerPk(questionPk, ownerPk)
        return interviewScriptMapper
            .toDomain(entity)
            .also { it.authenticate(ownerPk) }
    }
}
