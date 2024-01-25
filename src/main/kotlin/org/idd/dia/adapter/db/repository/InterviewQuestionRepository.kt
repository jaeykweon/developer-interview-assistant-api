package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.adapter.api.dropNull
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InterviewQuestionRepository(
    private val jpaRepository: InterviewQuestionJpaRepository,
) : InterviewQuestionDbPort {
    override fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity {
        return jpaRepository.save(questionEntity)
    }

    override fun getPageWithRelations(
        previousPk: InterviewQuestion.Pk?,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity> {
        val result =
            jpaRepository.findPage(pageable) {
                select(
                    entity(InterviewQuestionEntity::class),
                ).from(
                    entity(InterviewQuestionEntity::class),
                ).where(
                    previousPk?.run { path(InterviewQuestionEntity::pkValue).le(this.value) },
                ).orderBy(
                    path(InterviewQuestionEntity::pkValue).desc(),
                )
            }

        return result.dropNull()
    }

    override fun getByPk(pk: InterviewQuestion.Pk): InterviewQuestionEntity {
        return jpaRepository.findByIdOrNull(pk.value)
            ?: throw IllegalArgumentException()
    }
}

interface InterviewQuestionJpaRepository : JpaRepository<InterviewQuestionEntity, Long>, KotlinJdslJpqlExecutor
