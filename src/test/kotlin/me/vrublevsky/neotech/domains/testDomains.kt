package me.vrublevsky.neotech.domains

import me.vrublevsky.neotech.domains.common.extensions.toDomain

object TestDomain {
    val google = "google.com".toDomain()
    val nonExisting = "nonexistingdomain.com".toDomain()
    val integrationInternalError = "error-internal.com".toDomain()
    val integrationError = "error.com".toDomain()
}
