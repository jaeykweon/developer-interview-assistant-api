package org.idd.dia.application.port.usingcase.mapping

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity

interface InterviewQuestionCategoryMappingDbPort {
    fun overwriteQuestionCategories(
        interviewQuestionEntity: InterviewQuestionEntity,
        interviewQuestionCategoryEntities: Set<InterviewQuestionCategoryEntity>,
    ): Set<InterviewQuestionCategoryMappingEntity>
}
