package org.idd.dia.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDateTime

@ApiV0RestController
class OthersRestController {
    @GetMapping("/health-check")
    fun healthCheck(): ApiResponse<String> {
        val serverTime = LocalDateTime.now()
        return ApiResponse.ok(serverTime.toString())
    }
}
