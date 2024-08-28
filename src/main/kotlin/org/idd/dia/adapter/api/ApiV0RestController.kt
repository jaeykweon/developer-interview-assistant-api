package org.idd.dia.adapter.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v0")
@RestController
annotation class ApiV0RestController

@RequestMapping("/api/v1")
@RestController
annotation class ApiV1RestController
