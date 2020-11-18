package me.vrublevsky.neotech.domains.api.v1.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.domain.DomainPrice
import me.vrublevsky.neotech.domains.domain.Registrar
import java.time.OffsetDateTime

data class DomainStatusResponse(
    val domain: Domain,
    val status: DomainStatus,
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "status"
)
@JsonSubTypes(
    JsonSubTypes.Type(
        value = RegisteredDomain::class,
        name = "registered"
    ),
    JsonSubTypes.Type(
        value = AvailableDomain::class,
        name = "available"
    ),
)

interface DomainStatus

data class RegisteredDomain(
    val registrar: Registrar?,
    val expirationDate: OffsetDateTime?,
) : DomainStatus

data class AvailableDomain(
    val prices: List<RegistrarDomainPrice>,
) : DomainStatus

data class RegistrarDomainPrice(
    val registrar: Registrar,
    val available: Boolean,
    val price: DomainPrice?,
)
