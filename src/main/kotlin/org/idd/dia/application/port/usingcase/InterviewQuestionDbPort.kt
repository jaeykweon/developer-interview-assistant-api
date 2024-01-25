package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionDbPort {
    fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity

    fun getPageWithRelations(
        previousPk: InterviewQuestion.Pk?,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity>

    fun getByPk(pk: InterviewQuestion.Pk): InterviewQuestionEntity
}
