package org.idd.dia.adapter.db.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.idd.dia.application.port.usingcase.InterviewQuestionCollectionDbPort
import org.idd.dia.domain.entity.InterviewQuestionCollectionEntity
import org.idd.dia.domain.model.InterviewQuestionCollection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class InterviewQuestionCollectionRepository(
    private val interviewQuestionCollectionJpaRepository: InterviewQuestionCollectionJpaRepository,
) : InterviewQuestionCollectionDbPort {
    override fun getAllEntitiesWithQuestionMappings(): List<InterviewQuestionCollectionEntity> {
        val query: Jpql.() -> SelectQuery<InterviewQuestionCollectionEntity> = {
            jpql {
                select(
                    entity(InterviewQuestionCollectionEntity::class),
                ).from(
                    entity(InterviewQuestionCollectionEntity::class),
                    fetchJoin(InterviewQuestionCollectionEntity::questionMappings),
                )
            }
        }

        return interviewQuestionCollectionJpaRepository.findAll(query).filterNotNull()
    }

    override fun getEntityWithQuestionMappings(pk: InterviewQuestionCollection.Pk): InterviewQuestionCollectionEntity? {
        val query: Jpql.() -> SelectQuery<InterviewQuestionCollectionEntity> = {
            jpql {
                select(
                    entity(InterviewQuestionCollectionEntity::class),
                ).from(
                    entity(InterviewQuestionCollectionEntity::class),
                    fetchJoin(InterviewQuestionCollectionEntity::questionMappings),
                ).where(
                    path(InterviewQuestionCollectionEntity::pkValue).eq(pk.value),
                )
            }
        }

        return interviewQuestionCollectionJpaRepository.findAll(query).firstOrNull()
    }
}

interface InterviewQuestionCollectionJpaRepository : JpaRepository<InterviewQuestionCollectionEntity, Long>, KotlinJdslJpqlExecutor
