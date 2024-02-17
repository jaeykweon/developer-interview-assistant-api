package org.idd.dia.adapter.config

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.idd.dia.adapter.config.PageableConfig.Companion.DEFAULT_PAGE_SIZE
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Component

/**
 * handler 네이밍이 맞나 이게
 */
@Component
class KotlinJdslHandler(
    @PersistenceContext
    private val entityManager: EntityManager,
) {
    private val jpqlRenderContext = JpqlRenderContext()

    fun <T : Any> executeScrollQuery(query: SelectQuery<T>): SliceImpl<T> {
        val maybeOneMoreResult =
            entityManager
                .createQuery(query, jpqlRenderContext)
                .resultList

        val next = maybeOneMoreResult.size > DEFAULT_PAGE_SIZE

        val realResult =
            if (next) {
                maybeOneMoreResult.dropLast(1)
            } else {
                maybeOneMoreResult
            }

        return SliceImpl(realResult, Pageable.unpaged(), next)
    }
}
