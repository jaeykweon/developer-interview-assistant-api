package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity

data class InterviewQuestionCategoryResponse(
    val pkValue: Long,
    val titleValue: String,
) {
    constructor(entity: InterviewQuestionCategoryEntity) : this(
        pkValue = entity.getPk().value,
        titleValue = entity.getKorTitle().value,
    )
}

data class InterviewQuestionCategoryAddRequest(
    val title: String,
)
