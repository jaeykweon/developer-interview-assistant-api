package org.idd.dia.adapter.db.repository.mapping

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.adapter.api.dropNull
import org.idd.dia.application.port.usingcase.mapping.InterviewQuestionBookmarkMappingDbPort
import org.idd.dia.domain.ConflictException
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InterviewQuestionBookmarkMappingRepository(
    private val interviewQuestionBookmarkMappingJpaRepository: InterviewQuestionBookmarkMappingJpaRepository,
) : InterviewQuestionBookmarkMappingDbPort {
    override fun getMappings(
        memberPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): List<InterviewQuestionBookmarkMappingEntity> {
        return getMappings(memberPk, listOf(questionEntity))
    }

    override fun getMappings(
        memberPk: Member.Pk,
        interviewQuestionEntities: List<InterviewQuestionEntity>,
    ): List<InterviewQuestionBookmarkMappingEntity> {
        return interviewQuestionBookmarkMappingJpaRepository
            .findAllByMemberPkValueAndQuestionIn(memberPk.value, interviewQuestionEntities)
    }

    override fun getMappingsWithQuestion(
        memberPk: Member.Pk,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity> {
        return getMappingsWithQuestion(memberPk, emptySet(), pageable)
    }

    override fun getMappingsWithQuestion(
        memberPk: Member.Pk,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity> {
        val pkValuePageQuery: Jpql.() -> SelectQuery<Long> = {
            val categoryClause =
                if (categoryEntities.isEmpty()) {
                    null
                } else {
                    path(InterviewQuestionCategoryEntity::pkValue).`in`(categoryEntities.map { it.pkValue })
                }
            jpql {
                select(
                    path(InterviewQuestionBookmarkMappingEntity::pkValue),
                ).from(
                    entity(InterviewQuestionBookmarkMappingEntity::class),
                    innerJoin(InterviewQuestionBookmarkMappingEntity::question),
                    innerJoin(InterviewQuestionEntity::categoryMappings),
                ).whereAnd(
                    path(InterviewQuestionBookmarkMappingEntity::memberPkValue).eq(memberPk.value),
                    categoryClause,
                ).orderBy(
                    path(InterviewQuestionBookmarkMappingEntity::pkValue).desc(),
                )
            }
        }

        val pkValuePage =
            interviewQuestionBookmarkMappingJpaRepository
                .findPage(pageable, pkValuePageQuery)
                .dropNull()

        val entitiesWithQuestion: List<InterviewQuestionBookmarkMappingEntity> =
            this.getMappingsWithQuestion(pkValuePage.content.map { it })

        return PageImpl(entitiesWithQuestion, pageable, pkValuePage.totalElements)
    }

    override fun getMappingsWithQuestion(pks: Iterable<Long>): List<InterviewQuestionBookmarkMappingEntity> {
        if (pks.none()) {
            return emptyList()
        }
        val query: Jpql.() -> SelectQuery<InterviewQuestionBookmarkMappingEntity> = {
            jpql {
                select(
                    entity(InterviewQuestionBookmarkMappingEntity::class),
                ).from(
                    entity(InterviewQuestionBookmarkMappingEntity::class),
                    fetchJoin(InterviewQuestionBookmarkMappingEntity::question),
                ).where(
                    path(InterviewQuestionBookmarkMappingEntity::pkValue).`in`(pks),
                )
            }
        }
        return interviewQuestionBookmarkMappingJpaRepository.findAll(query).filterNotNull()
    }

    override fun addBookmark(
        memberPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): InterviewQuestionBookmarkMappingEntity {
        if (isBookmarkExist(memberPk, questionEntity)) {
            throw ConflictException("Bookmark already exists")
        }
        return interviewQuestionBookmarkMappingJpaRepository.save(
            InterviewQuestionBookmarkMappingEntity.new(
                question = questionEntity,
                memberPk = memberPk,
                createdTime = LocalDateTime.now(),
            ),
        )
    }

    override fun removeBookmark(
        memberPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): Long {
        if (!isBookmarkExist(memberPk, questionEntity)) {
            // todo: 이것은 bad request, not found, conflict 중 어떤 예외인가
            throw NotFoundException("Bookmark already exists")
        }
        val old = interviewQuestionBookmarkMappingJpaRepository.findByMemberPkValueAndQuestion(memberPk.value, questionEntity)!!
        interviewQuestionBookmarkMappingJpaRepository.deleteById(old.pkValue)
        return old.pkValue
    }

    private fun isBookmarkExist(
        ownerPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): Boolean {
        return interviewQuestionBookmarkMappingJpaRepository.existsByQuestionAndMemberPkValue(questionEntity, ownerPk.value)
    }
}

interface InterviewQuestionBookmarkMappingJpaRepository :
    JpaRepository<InterviewQuestionBookmarkMappingEntity, Long>,
    KotlinJdslJpqlExecutor {
    fun findByMemberPkValueAndQuestion(
        memberPkValue: Long,
        question: InterviewQuestionEntity,
    ): InterviewQuestionBookmarkMappingEntity?

    fun findAllByMemberPkValueAndQuestionIn(
        memberPkValue: Long,
        questions: List<InterviewQuestionEntity>,
    ): List<InterviewQuestionBookmarkMappingEntity>

    fun existsByQuestionAndMemberPkValue(
        question: InterviewQuestionEntity,
        ownerPk: Long,
    ): Boolean
}
