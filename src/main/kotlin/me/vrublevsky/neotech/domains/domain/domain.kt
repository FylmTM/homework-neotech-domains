package me.vrublevsky.neotech.domains.domain

import java.math.BigDecimal
import java.util.*

data class Domain(
    val original: String,
    val normalized: String,
)

data class DomainPrice(
    val amount: BigDecimal,
    val currency: Currency,
)
