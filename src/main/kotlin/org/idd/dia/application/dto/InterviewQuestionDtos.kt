package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.entity.mapping.isBookmarked
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
    val titleValue: String,
    val categories: Iterable<InterviewQuestionCategoryResponse>,
    val voices: Iterable<InterviewQuestionVoiceResponse>,
    val bookmark: Boolean,
) {
    companion object {
        @JvmStatic
        fun withoutCheckingBookmark(interviewQuestionEntity: InterviewQuestionEntity): InterviewQuestionResponse {
            val categories: List<InterviewQuestionCategoryResponse> =
                interviewQuestionEntity.categoryMappings.map {
                    InterviewQuestionCategoryResponse(it.category)
                }
            val voices: List<InterviewQuestionVoiceResponse> =
                interviewQuestionEntity.voices.map {
                    InterviewQuestionVoiceResponse(it)
                }
            return InterviewQuestionResponse(
                pkValue = interviewQuestionEntity.pkValue,
                korTitleValue = interviewQuestionEntity.titleValue,
                titleValue = interviewQuestionEntity.titleValue,
                categories = categories,
                voices = voices,
                bookmark = false,
            )
        }

        /**
         * InterviewQuestionBookmarkMappingEntity는 해당 테이블만 있으면 됌 (fetch join 해올 필요 없음)
         */
        @JvmStatic
        fun withCheckingBookmark(
            interviewQuestionEntity: InterviewQuestionEntity,
            interviewQuestionBookmarkMappingsEntity: Iterable<InterviewQuestionBookmarkMappingEntity>,
        ): InterviewQuestionResponse {
            val categories: Iterable<InterviewQuestionCategoryResponse> =
                interviewQuestionEntity.categoryMappings.map {
                    InterviewQuestionCategoryResponse(it.category)
                }
            val voices =
                interviewQuestionEntity.voices.map {
                    InterviewQuestionVoiceResponse(it)
                }
            val bookmarked = interviewQuestionBookmarkMappingsEntity.isBookmarked(interviewQuestionEntity)
            return InterviewQuestionResponse(
                pkValue = interviewQuestionEntity.pkValue,
                korTitleValue = interviewQuestionEntity.titleValue,
                titleValue = interviewQuestionEntity.titleValue,
                categories = categories,
                voices = voices,
                bookmark = bookmarked,
            )
        }
    }
}

data class SetCategoriesOfInterviewQuestionRequest(
    val categoryPkValues: Set<Long>,
) {
    fun getCategoryPks(): Set<InterviewQuestionCategory.Pk> = categoryPkValues.mapToSet { InterviewQuestionCategory.Pk(it) }
}

data class InterviewQuestionBookmarkResultResponse(
    val bookmarked: Boolean,
) {
    companion object {
        @JvmStatic
        fun bookmarked(): InterviewQuestionBookmarkResultResponse {
            return InterviewQuestionBookmarkResultResponse(true)
        }

        @JvmStatic
        fun unBookmarked(): InterviewQuestionBookmarkResultResponse {
            return InterviewQuestionBookmarkResultResponse(false)
        }
    }
}
