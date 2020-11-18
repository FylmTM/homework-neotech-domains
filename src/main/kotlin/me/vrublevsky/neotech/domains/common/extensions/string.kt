package me.vrublevsky.neotech.domains.common.extensions

import me.vrublevsky.neotech.domains.domain.Domain
import java.math.BigDecimal
import java.net.IDN
import java.util.*

/**
 * Convert unicode characters in domain name to punycode.
 */
fun String.normalizeDomainName(): String = IDN.toASCII(this)

fun String.toDomain() = Domain(
    original = this,
    normalized = this.normalizeDomainName()
)

fun String.toBigDecimal() = BigDecimal(this)

fun String.toCurrency(): Currency = Currency.getInstance(this)
