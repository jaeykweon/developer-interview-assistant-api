package org.idd.dia.adapter.db.repository.mapping

import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionCategoryMappingDbPort
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * question: interview question 또는 interview question category 어느쪽에 소속시켜야할까
 */
@Repository
class InterviewQuestionCategoryMappingRepository(
    private val interviewQuestionCategoryMappingJpaRepository: InterviewQuestionCategoryMappingJpaRepository,
) : InterviewQuestionCategoryMappingDbPort {
    override fun overwriteQuestionCategories(
        interviewQuestionEntity: InterviewQuestionEntity,
        interviewQuestionCategoryEntities: Set<InterviewQuestionCategoryEntity>,
    ): Set<InterviewQuestionCategoryMappingEntity> {
        val old = interviewQuestionCategoryMappingJpaRepository.getAllByQuestion(interviewQuestionEntity)
        val new =
            interviewQuestionCategoryEntities
                .map {
                    InterviewQuestionCategoryMappingEntity.new(
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
