package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionEntity

data class InterviewQuestionResponse(
    val pk: Long,
    val title: String
){
    constructor(entity: InterviewQuestionEntity): this(
        pk = entity.pk,
        title = entity.title.value
    )

}
