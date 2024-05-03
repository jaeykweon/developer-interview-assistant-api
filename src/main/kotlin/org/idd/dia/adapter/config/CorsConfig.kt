package org.idd.dia.adapter.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedMethods(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "HEAD",
                "OPTIONS",
            )
            .allowedHeaders(
                "Authorization",
                "Content-Type",
            )
            .allowedOrigins(
                "http://localhost:3000",
                "https://with.d-i-a.run/",
                "https://staging.with.d-i-a.run/",
            )
    }
}
