package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.adapter.api.dropNull
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
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

    fun getPageWithRelations(
        interviewQuestionCategoryEntities: Iterable<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): PageImpl<InterviewQuestionEntity> {
        val pkValuePageQuery: Jpql.() -> SelectQuery<Long> = {
            jpql {
                select(
                    path(InterviewQuestionEntity::pkValue),
                ).from(
                    entity(InterviewQuestionEntity::class),
                    innerJoin(InterviewQuestionEntity::categories),
                    innerJoin(InterviewQuestionCategoryMappingEntity::category),
                ).where(
                    path(InterviewQuestionCategoryMappingEntity::category).`in`(interviewQuestionCategoryEntities),
                ).orderBy(
                    path(InterviewQuestionEntity::pkValue).desc(),
                )
            }
        }

        val pkValuePage: PageImpl<Long> =
            jpaRepository
                .findPage(pageable, pkValuePageQuery)
                .dropNull()

        val pks = pkValuePage.content.map(InterviewQuestion::Pk)
        return PageImpl(this.getEntitiesWithRelations(pks), pageable, pkValuePage.totalElements)
    }

    override fun getPageWithRelations(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity> {
        val pkPageQuery: Jpql.() -> SelectQuery<Long> = {
            val categoryClause =
                if (categories.isEmpty()) {
                    null
                } else {
                    path(InterviewQuestionCategoryEntity::engTitleValue).`in`(categories.map { it.value })
                }
            jpql {
                selectDistinct(
                    path(InterviewQuestionEntity::pkValue),
                ).from(
                    entity(InterviewQuestionEntity::class),
                    innerJoin(InterviewQuestionEntity::categories),
                    innerJoin(InterviewQuestionCategoryMappingEntity::category),
                    innerJoin(InterviewQuestionEntity::voices),
                ).whereAnd(
                    categoryClause,
                ).orderBy(
                    path(InterviewQuestionEntity::pkValue).desc(),
                )
            }
        }
        val pkPageResult = jpaRepository.findPage(pageable, pkPageQuery).dropNull()
        val pks = pkPageResult.content.map { InterviewQuestion.Pk(it) }

        return PageImpl(this.getEntitiesWithRelations(pks), pageable, pkPageResult.totalElements)
    }

    override fun getWithOutRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity {
        return jpaRepository.findByIdOrNull(pk.value)
            ?: throw NotFoundException("InterviewQuestionEntity with pk: $pk not found")
    }

    override fun getEntityWithRelations(pk: InterviewQuestion.Pk): InterviewQuestionEntity {
        val query: Jpql.() -> SelectQuery<InterviewQuestionEntity> = {
            jpql {
                select(
                    entity(InterviewQuestionEntity::class),
                ).from(
                    entity(InterviewQuestionEntity::class),
                    fetchJoin(InterviewQuestionEntity::categories),
                    fetchJoin(InterviewQuestionCategoryMappingEntity::category),
                    fetchJoin(InterviewQuestionEntity::voices),
                ).where(
                    path(InterviewQuestionEntity::pkValue).`in`(pk.value),
                )
            }
        }
        return jpaRepository.findAll(query).firstOrNull()
            ?: throw NotFoundException("InterviewQuestionEntity with pk: $pk not found")
    }

    override fun getEntitiesWithRelations(pks: Iterable<InterviewQuestion.Pk>): List<InterviewQuestionEntity> {
        if (pks.none()) return emptyList()

        val query: Jpql.() -> SelectQuery<InterviewQuestionEntity> = {
            jpql {
                select(
                    entity(InterviewQuestionEntity::class),
                ).from(
                    entity(InterviewQuestionEntity::class),
                    fetchJoin(InterviewQuestionEntity::categories),
                    fetchJoin(InterviewQuestionCategoryMappingEntity::category),
                    fetchJoin(InterviewQuestionEntity::voices),
                ).where(
                    path(InterviewQuestionEntity::pkValue).`in`(pks.map { it.value }),
                )
            }
        }
        return jpaRepository.findAll(query).filterNotNull()
    }
}

interface InterviewQuestionJpaRepository : JpaRepository<InterviewQuestionEntity, Long>, KotlinJdslJpqlExecutor
