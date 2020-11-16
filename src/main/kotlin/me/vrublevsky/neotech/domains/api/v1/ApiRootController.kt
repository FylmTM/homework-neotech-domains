package me.vrublevsky.neotech.domains.api.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ApiRootController {

    @GetMapping
    fun index(): EmptyApiResponse = emptyApiResponse
}
