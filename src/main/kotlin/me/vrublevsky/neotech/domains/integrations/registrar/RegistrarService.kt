package me.vrublevsky.neotech.domains.integrations.registrar

import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.domain.DomainPrice

interface RegistrarService {

    val name: String

    fun getInformation(domain: Domain): DomainRegistrarInformation
}

/**
 * Represents ADT for different result types.
 */
sealed class DomainRegistrarInformation

data class AvailableDomainRegistrarInformation(
    /**
     * Price for one year.
     */
    val price: DomainPrice
) : DomainRegistrarInformation()

object NotAvailableDomainRegistrarInformation : DomainRegistrarInformation()

object MissingDomainRegistrarInformation : DomainRegistrarInformation()
