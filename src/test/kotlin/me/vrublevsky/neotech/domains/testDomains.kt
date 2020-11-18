package me.vrublevsky.neotech.domains

import me.vrublevsky.neotech.domains.common.extensions.toDomain

object TestDomain {
    val notAvailable = "google.com".toDomain()
    val available = "inexisting-domain-for-testing.com".toDomain()
    val invalidTld = "domain.invalidtld".toDomain()
    val integrationError = "error.com".toDomain()
    val integrationInternalError = "error-internal.com".toDomain()
}
