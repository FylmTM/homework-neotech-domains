package me.vrublevsky.neotech.domains.api.v1.domain

import me.vrublevsky.neotech.domains.common.api.ApiResponse
import me.vrublevsky.neotech.domains.common.api.ok
import me.vrublevsky.neotech.domains.common.extensions.toDomain
import me.vrublevsky.neotech.domains.common.validations.Domain
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/domain")
@Validated
class DomainController(
    private val service: DomainStatusService,
) {

    @GetMapping("/{domainName}/status")
    fun status(
        @PathVariable("domainName")
        @Domain
        domainName: String,
    ): ApiResponse<DomainStatusResponse> =
        domainName
            .toDomain()
            .let {
                val status = service.getStatus(it)
                DomainStatusResponse(
                    domain = it,
                    status = status
                )
            }
            .ok()
}
