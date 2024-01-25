package org.idd.dia.adapter.db.repository.mapping

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class InterviewQuestionCategoryMappingRepository(
    private val interviewQuestionCategoryMappingJpaRepository: InterviewQuestionCategoryMappingJpaRepository,
) {
    fun overwriteQuestionCategories(
        interviewQuestionEntity: InterviewQuestionEntity,
        interviewQuestionCategoryEntities: Set<InterviewQuestionCategoryEntity>,
    ): Set<InterviewQuestionCategoryMappingEntity> {
        val old = interviewQuestionCategoryMappingJpaRepository.getAllByQuestion(interviewQuestionEntity)
        val new =
            interviewQuestionCategoryEntities
                .map {
                    InterviewQuestionCategoryMappingEntity(
                        pk = 0,
                        question = interviewQuestionEntity,
                        category = it,
                    )
                }.toSet()

        interviewQuestionCategoryMappingJpaRepository.deleteAll(old)
        return interviewQuestionCategoryMappingJpaRepository.saveAll(new).toSet()
    }
}

interface InterviewQuestionCategoryMappingJpaRepository : JpaRepository<InterviewQuestionCategoryMappingEntity, Long> {
    fun getAllByQuestion(question: InterviewQuestionEntity): Set<InterviewQuestionCategoryMappingEntity>
}
