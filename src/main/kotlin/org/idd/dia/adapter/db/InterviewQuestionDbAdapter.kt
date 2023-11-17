package org.idd.dia.adapter.db

import org.idd.dia.adapter.db.entity.InterviewQuestionEntity
import org.idd.dia.adapter.db.mapper.InterviewQuestionMapper
import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.application.port.out.InterviewQuestionDbPort
import org.idd.dia.domain.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class InterviewQuestionDbAdapter(
    private val questionRepository: InterviewQuestionRepository,
    private val questionMapper: InterviewQuestionMapper
) : InterviewQuestionDbPort {
    override fun save(question: InterviewQuestion): InterviewQuestion {
        val beforeSave: InterviewQuestionEntity = questionMapper.toDbEntity(question)
        val afterSave: InterviewQuestionEntity = questionRepository.save(beforeSave)
        return questionMapper.toDomainModel(afterSave)
    }

    override fun getInterviewQuestions(pageable: Pageable): Page<InterviewQuestion> {
        val entities = questionRepository.getPage(pageable)
        return entities.map { questionMapper.toDomainModel(it) }
    }

    override fun getInterviewQuestion(pk: InterviewQuestion.Pk): InterviewQuestion {
        val entity = questionRepository.get(pk)
        return questionMapper.toDomainModel(entity)
    }
}
