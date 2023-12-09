package org.idd.dia.application.dto

import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript

data class InterviewScriptCreateRequest(
    val questionPk: InterviewQuestion.Pk,
    val content: InterviewScript.Content
)

data class InterviewScriptUpdateRequest(
    val questionPk: InterviewQuestion.Pk,
    val content: InterviewScript.Content
)
