package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionDbPort {
    fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity

    fun getWithOutRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity

    fun getEntityWithRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity

    fun getEntitiesWithRelations(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionEntity>

    fun getPageWithRelations(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity>
}
