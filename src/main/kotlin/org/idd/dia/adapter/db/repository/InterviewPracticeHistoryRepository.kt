package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.adapter.config.KotlinJdslHandler
import org.idd.dia.application.port.usingcase.InterviewPracticeHistoryDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InterviewPracticeHistoryRepository(
    private val interviewPracticeHistoryJpaRepository: InterviewPracticeHistoryJpaRepository,
    private val kotlinJdslHandler: KotlinJdslHandler,
) : InterviewPracticeHistoryDbPort {
    override fun save(entity: InterviewPracticeHistoryEntity): InterviewPracticeHistoryEntity {
        return interviewPracticeHistoryJpaRepository.save(entity)
    }

    override fun getScrollAfterPk(
        memberEntity: MemberEntity,
        pk: InterviewPracticeHistory.Pk,
    ): CustomScroll<InterviewPracticeHistoryEntity> {
        val query =
            jpql {
                select(
                    entity(InterviewPracticeHistoryEntity::class),
                ).from(
                    entity(InterviewPracticeHistoryEntity::class),
                )
                    .whereAnd(
                        path(InterviewPracticeHistoryEntity::owner).eq(memberEntity),
                        path(InterviewPracticeHistoryEntity::pkValue).le(pk.value),
                    ).orderBy(
                        path(InterviewPracticeHistoryEntity::pkValue).desc(),
                    )
            }
        return kotlinJdslHandler.executeScrollQuery(query)
    }

    override fun getByPk(pk: InterviewPracticeHistory.Pk): InterviewPracticeHistoryEntity {
        return interviewPracticeHistoryJpaRepository.findByIdOrNull(pk.value)
            ?: throw NotFoundException("InterviewPracticeHistory not found. pk: $pk")
    }

    override fun deleteByPk(pk: InterviewPracticeHistory.Pk) {
        interviewPracticeHistoryJpaRepository.deleteById(pk.value)
    }
}

interface InterviewPracticeHistoryJpaRepository : JpaRepository<InterviewPracticeHistoryEntity, Long>, KotlinJdslJpqlExecutor
