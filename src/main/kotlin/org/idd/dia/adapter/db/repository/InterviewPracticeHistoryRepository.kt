package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.adapter.config.KotlinJdslHandler
import org.idd.dia.application.port.usingcase.InterviewPracticeHistoryDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewPracticeHistoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.model.InterviewPracticeHistory
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class InterviewPracticeHistoryRepository(
    private val interviewPracticeHistoryJpaRepository: InterviewPracticeHistoryJpaRepository,
    private val kotlinJdslHandler: KotlinJdslHandler,
) : InterviewPracticeHistoryDbPort {
    override fun save(entity: InterviewPracticeHistoryEntity): InterviewPracticeHistoryEntity {
        return interviewPracticeHistoryJpaRepository.save(entity)
    }

    override fun getScroll(
        memberPk: Member.Pk,
        previousPk: InterviewPracticeHistory.Pk?,
        interviewQuestionEntity: InterviewQuestionEntity?,
        star: Boolean?,
        pageable: Pageable,
    ): Slice<InterviewPracticeHistoryEntity> {
        val query =
            jpql {
                select(
                    entity(InterviewPracticeHistoryEntity::class),
                ).from(
                    entity(InterviewPracticeHistoryEntity::class),
                ).whereAnd(
                    path(InterviewPracticeHistoryEntity::memberPkValue).eq(memberPk.value),
                    previousPk?.let {
                        path(InterviewPracticeHistoryEntity::pkValue).le(it.value)
                    },
                    interviewQuestionEntity?.let {
                        path(InterviewPracticeHistoryEntity::question).eq(it)
                    },
                    star?.let {
                        path(InterviewPracticeHistoryEntity::starValue).eq(it)
                    },
                    path(InterviewPracticeHistoryEntity::deletedValue).eq(false),
                ).orderBy(
                    path(InterviewPracticeHistoryEntity::pkValue).desc(),
                )
            }
        return kotlinJdslHandler.executeScrollQuery(query, pageable)
    }

    override fun getSingleEntity(
        pk: InterviewPracticeHistory.Pk,
        memberPk: Member.Pk,
    ): InterviewPracticeHistoryEntity {
        val target =
            interviewPracticeHistoryJpaRepository.findByPkValueAndMemberPkValue(pk.value, memberPk.value)
                ?: throw NotFoundException("InterviewPracticeHistory not found. pk: $pk memberPk: $memberPk")
        return target
    }
}

interface InterviewPracticeHistoryJpaRepository : JpaRepository<InterviewPracticeHistoryEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByPkValueAndMemberPkValue(
        pkValue: Long,
        memberPkValue: Long,
    ): InterviewPracticeHistoryEntity?
}
