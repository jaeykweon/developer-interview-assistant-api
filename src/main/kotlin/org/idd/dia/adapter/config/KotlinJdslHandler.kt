package org.idd.dia.adapter.config

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Component

/**
 * todo: 이 handler를 어디따 옮기지
 */
@Component
class KotlinJdslHandler(
    @PersistenceContext
    private val entityManager: EntityManager,
) {
    private val jpqlRenderContext = JpqlRenderContext()

    fun <T : Any> executeScrollQuery(
        query: SelectQuery<T>,
        pageable: Pageable,
    ): SliceImpl<T> {
        val maybeOneMoreResult =
            entityManager
                .createQuery(query, jpqlRenderContext)
                .setMaxResults(pageable.pageSize + 1)
                .resultList

        val next = maybeOneMoreResult.size > pageable.pageSize

        val realResult =
            if (next) {
                maybeOneMoreResult.dropLast(1)
            } else {
                maybeOneMoreResult
            }

        return SliceImpl(realResult, Pageable.unpaged(), next)
    }
}
