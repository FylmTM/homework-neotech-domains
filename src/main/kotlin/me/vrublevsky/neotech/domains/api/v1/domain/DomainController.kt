package me.vrublevsky.neotech.domains.api.v1.domain

import me.vrublevsky.neotech.domains.common.api.ApiResponse
import me.vrublevsky.neotech.domains.common.api.ok
import me.vrublevsky.neotech.domains.common.validations.Domain
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/domain")
@Validated
class DomainController(
    private val service: DomainStatusService,
) {

    @GetMapping("/status")
    fun status(
        @RequestParam("domain")
        @Domain
        domain: String
    ): ApiResponse<DomainStatus> =
        service.getStatus(domain).ok()
}
