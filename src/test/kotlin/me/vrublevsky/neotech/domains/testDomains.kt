package me.vrublevsky.neotech.domains

import me.vrublevsky.neotech.domains.common.extensions.toDomain

object TestDomain {
    val google = "google.com".toDomain()
    val available = "inexisting-domain-for-testing.com".toDomain()
    val integrationError = "error.com".toDomain()
    val integrationInternalError = "error-internal.com".toDomain()
}
