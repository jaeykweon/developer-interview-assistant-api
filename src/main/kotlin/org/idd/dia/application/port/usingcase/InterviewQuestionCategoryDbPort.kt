package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.model.InterviewQuestionCategory

interface InterviewQuestionCategoryDbPort {
    fun getEntities(pks: Set<InterviewQuestionCategory.Pk>): Set<InterviewQuestionCategoryEntity>

    fun getEntities(titles: Collection<InterviewQuestionCategory.Title>): Set<InterviewQuestionCategoryEntity>
}
