package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.util.mapToSet

data class RegisterInterviewQuestionRequest(
    val titleValue: String,
    val categoryPkValues: Set<Long>,
) {
    fun getCategoryPks(): Set<InterviewQuestionCategory.Pk> {
        return categoryPkValues.mapToSet { InterviewQuestionCategory.Pk(it) }
    }

    fun getTitle(): InterviewQuestion.Title {
        return InterviewQuestion.Title(titleValue)
    }
}

data class InterviewQuestionResponse(
    val pkValue: Long,
    val korTitleValue: String,
    val categories: Set<InterviewQuestionCategoryResponse>,
    val voices: Set<InterviewQuestionVoiceResponse>,
) {
    companion object {
        @JvmStatic
        fun from(interviewQuestionEntity: InterviewQuestionEntity): InterviewQuestionResponse {
            val categories: Set<InterviewQuestionCategoryResponse> =
                interviewQuestionEntity.categories.mapToSet {
                    InterviewQuestionCategoryResponse(it.category)
                }
            val voices =
                interviewQuestionEntity.voices.mapToSet {
                    InterviewQuestionVoiceResponse(it)
                }
            return InterviewQuestionResponse(
                pkValue = interviewQuestionEntity.pkValue,
                korTitleValue = interviewQuestionEntity.korTitleValue,
                categories = categories,
                voices = voices,
            )
        }
    }
}

data class SetCategoriesOfInterviewQuestionRequest(
    val categoryPkValues: Set<Long>,
) {
    fun getCategoryPks(): Set<InterviewQuestionCategory.Pk> = categoryPkValues.mapToSet { InterviewQuestionCategory.Pk(it) }
}
