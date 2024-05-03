package org.idd.dia.adapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer

@Configuration
class PageableConfig {
    @Bean
    fun customize(): PageableHandlerMethodArgumentResolverCustomizer {
        return PageableHandlerMethodArgumentResolverCustomizer { resolver: PageableHandlerMethodArgumentResolver ->
            resolver.setMaxPageSize(MAX_PAGE_SIZE) // pageable max size
        }
    }

    companion object {
        const val MAX_PAGE_SIZE = 20
    }
}
