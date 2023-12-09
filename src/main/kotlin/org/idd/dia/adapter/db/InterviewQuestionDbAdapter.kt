package org.idd.dia.adapter.db

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.adapter.db.mapper.InterviewQuestionMapper
import org.idd.dia.adapter.db.repository.InterviewQuestionRepository
import org.idd.dia.application.port.out.InterviewQuestionDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class InterviewQuestionDbAdapter(
    private val questionRepository: InterviewQuestionRepository,
) : InterviewQuestionDbPort {

    fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity {
        return questionRepository.save(questionEntity)
    }
}
