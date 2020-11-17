package me.vrublevsky.neotech.domains.api.v1.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import me.vrublevsky.neotech.domains.domain.DomainPrice
import me.vrublevsky.neotech.domains.domain.Registrar
import java.time.OffsetDateTime

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
        value = FreeDomain::class,
        name = "free"
    ),
)
interface DomainStatus

data class RegisteredDomain(
    val registrar: Registrar,
    val expirationDate: OffsetDateTime,
) : DomainStatus

data class FreeDomain(
    val prices: List<RegistrarDomainPrice>,
) : DomainStatus

data class RegistrarDomainPrice(
    val registrar: Registrar,
    val price: DomainPrice,
)
