package org.idd.dia.application.port.out

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionDbPort {
    fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity
}
