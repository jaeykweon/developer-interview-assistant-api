package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity

data class InterviewQuestionCategoryResponse(
    val pkValue: Long,
    val titleValue: String,
) {
    constructor(entity: InterviewQuestionCategoryEntity) : this(
        pkValue = entity.pkValue,
        titleValue = entity.korTitleValue,
    )
}

data class InterviewQuestionCategoryAddRequest(
    val title: String,
)
