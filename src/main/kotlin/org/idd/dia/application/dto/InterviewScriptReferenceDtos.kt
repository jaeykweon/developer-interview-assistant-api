package org.idd.dia.application.dto

import org.idd.dia.domain.model.InterviewScriptReference

/**
 * 해당 질문에 가장 많이 인용된 레퍼런스
 */
data class MostQuotedReferencesOfQuestion(
    private val values: Set<InterviewScriptReference>,
) {
    init {
        validateSize()
        validateSameQuestion()
    }

    private fun validateSize() {
        require(values.size == SIZE)
    }

    private fun validateSameQuestion() {
//        val questionIds = values.map { it }.toSet()
//        require(questionIds.size == 1)
    }

    fun getValues() = this.values

    companion object {
        private const val SIZE = 4
    }
}
