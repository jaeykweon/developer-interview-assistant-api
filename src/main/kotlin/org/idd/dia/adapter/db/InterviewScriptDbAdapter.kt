package org.idd.dia.adapter.db

import org.idd.dia.adapter.db.mapper.InterviewScriptMapper
import org.idd.dia.adapter.db.repository.InterviewScriptRepository
import org.idd.dia.application.port.out.InterviewScriptDbPort
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.springframework.stereotype.Component

@Component
class InterviewScriptDbAdapter(
    private val interviewScriptMapper: InterviewScriptMapper,
    private val interviewScriptRepository: InterviewScriptRepository
) : InterviewScriptDbPort {
    override fun save(interviewScript: InterviewScript): InterviewScript {
        val newEntity = interviewScriptMapper.toEntity(interviewScript)
        val savedEntity = interviewScriptRepository.save(newEntity)
        return interviewScriptMapper.toDomain(savedEntity)
    }

    override fun get(pk: InterviewScript.Pk): InterviewScript {
        val entity = interviewScriptRepository.get(pk)
        return interviewScriptMapper.toDomain(entity)
    }

    override fun get(questionPk: InterviewQuestion.Pk): InterviewScript {
        val entity = interviewScriptRepository.get(questionPk = questionPk)
        return interviewScriptMapper.toDomain(entity)
    }
}
