package org.idd.dia.application.dto

import org.idd.dia.domain.model.InterviewQuestionVoice
import org.idd.dia.util.mapToSet

data class InterviewQuestionVoicesOfSingleQuestion(
    val values: Set<InterviewQuestionVoice>
) {
    init {
        validateSingleQuestion()
    }

    private fun validateSingleQuestion() {
        require(values.mapToSet { it.getQuestionPk() }.size == 1)
    }
}
