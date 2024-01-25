package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.adapter.config.KotlinJdslHandler
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.CustomScroll
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class InterviewPracticeHistoryRepository(
    private val interviewPracticeHistoryJpaRepository: InterviewPracticeHistoryJpaRepository,
    private val kotlinJdslHandler: KotlinJdslHandler,
) {
    fun save(entity: InterviewPracticeHistoryEntity): InterviewPracticeHistoryEntity {
        return interviewPracticeHistoryJpaRepository.save(entity)
    }

    fun getScrollAfterPk(
        memberEntity: MemberEntity,
        pk: InterviewPracticeHistory.Pk,
    ): CustomScroll<InterviewPracticeHistoryEntity> {
        val query =
            jpql {
                select(
                    entity(InterviewPracticeHistoryEntity::class),
                ).from(
                    entity(InterviewPracticeHistoryEntity::class),
                ).whereAnd(
                    path(InterviewPracticeHistoryEntity::owner).eq(memberEntity),
                    path(InterviewPracticeHistoryEntity::pkValue).le(pk.value),
                ).orderBy(
                    path(InterviewPracticeHistoryEntity::pkValue).desc(),
                )
            }
        return kotlinJdslHandler.executeScrollQuery(query)
    }
}

interface InterviewPracticeHistoryJpaRepository : JpaRepository<InterviewPracticeHistoryEntity, Long>, KotlinJdslJpqlExecutor
