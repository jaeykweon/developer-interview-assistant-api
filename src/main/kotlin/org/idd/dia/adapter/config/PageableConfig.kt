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
            resolver.setMaxPageSize(20) // pageable max size
        }
    }
}
