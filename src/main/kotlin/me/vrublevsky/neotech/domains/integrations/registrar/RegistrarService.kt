package me.vrublevsky.neotech.domains.integrations.registrar

import me.vrublevsky.neotech.domains.domain.DomainPrice

interface RegistrarService {

    fun getInformation(): DomainRegistrarInformation
}

sealed class DomainRegistrarInformation

data class FreeDomainRegistrarInformation(
    val price: DomainPrice
) : DomainRegistrarInformation()

object PurchasedDomainRegistrarInformation : DomainRegistrarInformation()
