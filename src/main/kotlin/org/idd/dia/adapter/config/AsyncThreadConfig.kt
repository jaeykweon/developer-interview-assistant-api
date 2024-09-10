package org.idd.dia.adapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncThreadConfig {
    @Bean
    fun myPool(): Executor {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.corePoolSize = 2 // 기본 스레드 수
        threadPoolTaskExecutor.maxPoolSize = 10 // 최대 스레드 수
        return threadPoolTaskExecutor
    }
}
