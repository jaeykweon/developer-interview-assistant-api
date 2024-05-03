package org.idd.dia.application.dto

import org.idd.dia.domain.entity.InterviewQuestionCollectionEntity

data class InterviewQuestionCollectionSimpleViewModels(
    val values: List<InterviewQuestionCollectionSimpleViewModel>,
) {
    companion object {
        @JvmStatic
        fun of(collectionEntities: List<InterviewQuestionCollectionEntity>): InterviewQuestionCollectionSimpleViewModels {
            val responses =
                collectionEntities.map { selectedEntity ->
                    InterviewQuestionCollectionSimpleViewModel.of(selectedEntity)
                }
            return InterviewQuestionCollectionSimpleViewModels(
                values = responses,
            )
        }
    }
}

data class InterviewQuestionCollectionSimpleViewModel(
    val pkValue: Long,
    val titleValue: String,
    val questionCountValue: Int,
) {
    companion object {
        @JvmStatic
        fun of(entity: InterviewQuestionCollectionEntity): InterviewQuestionCollectionSimpleViewModel {
            return InterviewQuestionCollectionSimpleViewModel(
                pkValue = entity.pkValue,
                titleValue = entity.titleValue,
                questionCountValue = entity.questionPkValues.size,
            )
        }
    }
}

data class InterviewQuestionCollectionDetailViewModel(
    val pkValue: Long,
    val titleValue: String,
    val questionAndScripts: List<InterviewScriptFormResponse>,
) {
    companion object {
        @JvmStatic
        fun of(
            entity: InterviewQuestionCollectionEntity,
            questionAndScripts: List<InterviewScriptFormResponse>,
        ): InterviewQuestionCollectionDetailViewModel {
            return InterviewQuestionCollectionDetailViewModel(
                pkValue = entity.pkValue,
                titleValue = entity.titleValue,
                questionAndScripts = questionAndScripts,
            )
        }
    }
}
