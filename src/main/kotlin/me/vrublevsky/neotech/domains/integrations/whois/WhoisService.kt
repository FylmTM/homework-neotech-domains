package me.vrublevsky.neotech.domains.integrations.whois

import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.domain.Registrar
import java.time.OffsetDateTime

interface WhoisService {

    /**
     * Return information about registered domain.
     * If domain is not registered return null.
     */
    fun getInformation(domain: Domain): DomainWhoisInformation?
}

data class DomainWhoisInformation(
    val registrar: Registrar,
    val expirationDate: OffsetDateTime,
)
