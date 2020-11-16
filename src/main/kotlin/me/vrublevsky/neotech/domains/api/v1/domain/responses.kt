package me.vrublevsky.neotech.domains.api.v1.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

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
    val registrar: String,
    val expirationDate: OffsetDateTime,
) : DomainStatus

data class FreeDomain(
    val price: BigDecimal,
    val currency: Currency,
) : DomainStatus
