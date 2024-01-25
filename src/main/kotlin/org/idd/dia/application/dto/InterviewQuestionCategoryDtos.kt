package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity

data class InterviewQuestionCategoryResponse(
    val pk: Long,
    val title: String,
) {
    constructor(entity: InterviewQuestionCategoryEntity) : this(
        pk = entity.getPk().value,
        title = entity.getKorTitle().value,
    )
}

data class InterviewQuestionCategoryAddRequest(
    val title: String,
)
