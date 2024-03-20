package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionDbPort {
    fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity

    fun getWithOutRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity

    fun getEntityWithCategoriesAndVoices(pk: InterviewQuestion.Pk): InterviewQuestionEntity

    fun getEntitiesWithCategoriesAndVoices(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionEntity>

    fun getPageWithCategoriesAndVoices(
        categoryEntities: Collection<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity>

    fun getPageWithCategories(
        interviewQuestionCategoryEntities: Iterable<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity>
}
