package org.idd.dia.adapter.db.repository

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.util.mapToSet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class InterviewQuestionCategoryRepository(
    private val interviewQuestionCategoryJpaRepository: InterviewQuestionCategoryJpaRepository,
) {
    fun getAllByPks(pks: Set<InterviewQuestionCategory.Pk>): Set<InterviewQuestionCategoryEntity> {
        val pkValues = pks.mapToSet { it.value }
        return interviewQuestionCategoryJpaRepository.findAllById(pkValues).toSet()
    }
}

interface InterviewQuestionCategoryJpaRepository : JpaRepository<InterviewQuestionCategoryEntity, Long>
