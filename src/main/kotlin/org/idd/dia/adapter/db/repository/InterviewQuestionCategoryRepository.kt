package org.idd.dia.adapter.db.repository

import org.idd.dia.application.port.usingcase.InterviewQuestionCategoryDbPort
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.idd.dia.util.mapToSet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class InterviewQuestionCategoryRepository(
    private val interviewQuestionCategoryJpaRepository: InterviewQuestionCategoryJpaRepository,
) : InterviewQuestionCategoryDbPort {
    override fun getEntities(pks: Set<InterviewQuestionCategory.Pk>): Set<InterviewQuestionCategoryEntity> {
        val pkValues = pks.mapToSet { it.value }
        return interviewQuestionCategoryJpaRepository.findAllById(pkValues).toSet()
    }

    override fun getEntities(titles: Collection<InterviewQuestionCategory.Title>): Collection<InterviewQuestionCategoryEntity> {
        val titleValues = titles.mapToSet { it.value }
        return interviewQuestionCategoryJpaRepository.findAllByEngTitleValueIn(titleValues).toSet()
    }
}

interface InterviewQuestionCategoryJpaRepository : JpaRepository<InterviewQuestionCategoryEntity, Long> {
    fun findAllByEngTitleValueIn(engTitleValues: Set<String>): List<InterviewQuestionCategoryEntity>
}
