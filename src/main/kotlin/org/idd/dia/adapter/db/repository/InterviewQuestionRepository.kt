package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities.entity
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
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
import org.springframework.stereotype.Repository

@Repository
class InterviewQuestionRepository(
    private val jpaRepository: InterviewQuestionJpaRepository,
) : InterviewQuestionDbPort {
    override fun save(questionEntity: InterviewQuestionEntity): InterviewQuestionEntity {
        return jpaRepository.save(questionEntity)
    }

    override fun getPageWithRelations(
        categories: Set<InterviewQuestionCategory.Title>,
        pageable: Pageable,
    ): Page<InterviewQuestionEntity> {
        val pksQuery: Jpql.() -> SelectQuery<Long> = {
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
        val pksResult = jpaRepository.findPage(pageable, pksQuery)

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
                    path(InterviewQuestionEntity::pkValue).`in`(pksResult.content),
                )
            }
        }

        val content = jpaRepository.findAll(query).filterNotNull()
        return PageImpl(content, pageable, pksResult.totalElements)
    }

    override fun getByPk(pk: InterviewQuestion.Pk): InterviewQuestionEntity {
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
                    path(InterviewQuestionEntity::pkValue).eq(pk.value),
                )
            }
        }
        return jpaRepository.findAll(query).firstOrNull()
            ?: throw NotFoundException("InterviewQuestionEntity with pk: $pk not found")
    }
}

interface InterviewQuestionJpaRepository : JpaRepository<InterviewQuestionEntity, Long>, KotlinJdslJpqlExecutor
