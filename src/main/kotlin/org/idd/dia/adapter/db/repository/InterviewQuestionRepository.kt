package org.idd.dia.adapter.db.repository

import org.idd.dia.adapter.db.entity.InterviewQuestionEntity
import org.idd.dia.domain.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class InterviewQuestionRepository(
    private val jpaRepository: InterviewQuestionJpaRepository
) {
    fun save(entity: InterviewQuestionEntity): InterviewQuestionEntity {
        return jpaRepository.save(entity)
    }

    fun getPage(pageable: Pageable): Page<InterviewQuestionEntity> {
        return jpaRepository.findAll(pageable)
    }

    fun get(pk: InterviewQuestion.Pk): InterviewQuestionEntity {
        return jpaRepository.findByIdOrNull(pk.value) ?: throw IllegalArgumentException()
    }
}

interface InterviewQuestionJpaRepository : JpaRepository<InterviewQuestionEntity, Long>
