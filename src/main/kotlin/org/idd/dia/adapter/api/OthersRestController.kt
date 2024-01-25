package org.idd.dia.adapter.api

import org.springframework.web.bind.annotation.GetMapping

@ApiV0RestController
class OthersRestController {
    @GetMapping("/health-check")
    fun healthCheck(): ApiResponse<String> = ApiResponse.ok("ok")
}
