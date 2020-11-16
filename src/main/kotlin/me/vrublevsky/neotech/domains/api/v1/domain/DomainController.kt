package me.vrublevsky.neotech.domains.api.v1.domain

import org.hibernate.validator.constraints.URL
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/domain")
class DomainController {

    @GetMapping
    fun get(@RequestParam("domain") @URL domain: String) {
    }
}
