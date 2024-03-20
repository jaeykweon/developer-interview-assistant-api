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
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
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
        ownerEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): List<InterviewQuestionBookmarkMappingEntity> {
        return getMappings(ownerEntity, listOf(questionEntity))
    }

    override fun getMappings(
        ownerEntity: MemberEntity,
        interviewQuestionEntities: List<InterviewQuestionEntity>,
    ): List<InterviewQuestionBookmarkMappingEntity> {
        return interviewQuestionBookmarkMappingJpaRepository
            .findAllByOwnerAndQuestionIn(ownerEntity, interviewQuestionEntities)
    }

    override fun getMappingsWithQuestion(
        ownerEntity: MemberEntity,
        categoryEntities: Iterable<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity> {
        val pkValuePageQuery: Jpql.() -> SelectQuery<Long> = {
            jpql {
                select(
                    path(InterviewQuestionBookmarkMappingEntity::pkValue),
                ).from(
                    entity(InterviewQuestionBookmarkMappingEntity::class),
                    innerJoin(InterviewQuestionBookmarkMappingEntity::question),
                    innerJoin(InterviewQuestionEntity::categoryMappings),
                ).whereAnd(
                    path(InterviewQuestionBookmarkMappingEntity::owner).eq(ownerEntity),
                    path(InterviewQuestionCategoryMappingEntity::category).`in`(categoryEntities),
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
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Long {
        if (isBookmarkExist(memberEntity, questionEntity)) {
            throw ConflictException("Bookmark already exists")
        }
        return interviewQuestionBookmarkMappingJpaRepository.save(
            InterviewQuestionBookmarkMappingEntity.new(
                question = questionEntity,
                owner = memberEntity,
                createdTime = LocalDateTime.now(),
            ),
        ).pkValue
    }

    override fun removeBookmark(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Long {
        if (!isBookmarkExist(memberEntity, questionEntity)) {
            // todo: 이것은 bad request, not found, conflict 중 어떤 예외인가
            throw NotFoundException("Bookmark already exists")
        }
        val old = interviewQuestionBookmarkMappingJpaRepository.findByOwnerAndQuestion(memberEntity, questionEntity)!!
        interviewQuestionBookmarkMappingJpaRepository.deleteById(old.pkValue)
        return old.pkValue
    }

    private fun isBookmarkExist(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Boolean {
        return interviewQuestionBookmarkMappingJpaRepository.existsByQuestionAndOwner(questionEntity, memberEntity)
    }
}

interface InterviewQuestionBookmarkMappingJpaRepository :
    JpaRepository<InterviewQuestionBookmarkMappingEntity, Long>,
    KotlinJdslJpqlExecutor {
    fun findByOwnerAndQuestion(
        owner: MemberEntity,
        question: InterviewQuestionEntity,
    ): InterviewQuestionBookmarkMappingEntity?

    fun findAllByOwnerAndQuestionIn(
        owner: MemberEntity,
        questions: List<InterviewQuestionEntity>,
    ): List<InterviewQuestionBookmarkMappingEntity>

    fun existsByQuestionAndOwner(
        question: InterviewQuestionEntity,
        owner: MemberEntity,
    ): Boolean
}
