package org.idd.dia.adapter.config

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.PersistenceContext
import org.idd.dia.domain.model.CustomScroll
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class KotlinJdslConfig {
    @Bean
    fun jpqlRenderContext(): JpqlRenderContext {
        return JpqlRenderContext()
    }

    companion object {
        const val MAX_RESULT_SIZE = 21
    }
}

@Component
class KotlinJdslHandler(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) {
    fun <T : Any> executeScrollQuery(query: SelectQuery<T>): CustomScroll<T> {
        val maybeOneMoreResult =
            entityManager
                .createQuery(query, jpqlRenderContext)
                .setMaxResults(KotlinJdslConfig.MAX_RESULT_SIZE)
                .resultList

        val next = maybeOneMoreResult.size > KotlinJdslConfig.MAX_RESULT_SIZE
        return CustomScroll(
            scrollData = maybeOneMoreResult.dropLast(1),
            next = next,
        )
    }
}
