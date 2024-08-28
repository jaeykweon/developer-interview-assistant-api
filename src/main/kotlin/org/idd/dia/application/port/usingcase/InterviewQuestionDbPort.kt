package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionDbPort {
    fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity

    fun getEntityWithOutRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity

    fun getEntitiesWithOutRelations(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionEntity>

    fun getEntityWithRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity

    fun getEntitiesWithRelations(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionEntity>

    fun getPageWithRelations(pageable: Pageable): Page<InterviewQuestionEntity>

    fun getPageWithRelations(
        categoryEntities: Collection<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity>
}
