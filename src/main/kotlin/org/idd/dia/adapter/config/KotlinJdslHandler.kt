package org.idd.dia.adapter.config

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.idd.dia.adapter.config.PageableConfig.Companion.DEFAULT_PAGE_SIZE
import org.idd.dia.domain.model.CustomScroll
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

    fun <T : Any> executeScrollQuery(query: SelectQuery<T>): CustomScroll<T> {
        val maybeOneMoreResult =
            entityManager
                .createQuery(query, jpqlRenderContext)
                .resultList

        val next = maybeOneMoreResult.size > DEFAULT_PAGE_SIZE
        return CustomScroll(
            scrollData = maybeOneMoreResult.dropLast(1),
            next = next,
        )
    }
}
