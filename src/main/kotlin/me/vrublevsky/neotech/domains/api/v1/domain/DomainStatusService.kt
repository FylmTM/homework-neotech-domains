package me.vrublevsky.neotech.domains.api.v1.domain

import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.integrations.registrar.AvailableDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.MissingDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.NotAvailableDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.RegistrarService
import me.vrublevsky.neotech.domains.integrations.whois.WhoisService
import org.springframework.stereotype.Service

@Service
class DomainStatusService(
    private val whois: WhoisService,
    private val registrars: List<RegistrarService>,
) {

    fun getStatus(domain: Domain): DomainStatus {
        val whoisInformation = whois.getInformation(domain)

        if (whoisInformation != null) {
            return RegisteredDomain(
                registrar = whoisInformation.registrar,
                expirationDate = whoisInformation.expirationDate
            )
        }

        val prices = registrars.map {
            when (val information = it.getInformation(domain)) {
                is AvailableDomainRegistrarInformation ->
                    RegistrarDomainPrice(
                        registrar = it.name,
                        available = true,
                        price = information.price
                    )
                is NotAvailableDomainRegistrarInformation ->
                    RegistrarDomainPrice(
                        registrar = it.name,
                        available = false,
                        price = null
                    )
                is MissingDomainRegistrarInformation ->
                    RegistrarDomainPrice(
                        registrar = it.name,
                        available = false,
                        price = null
                    )
            }
        }

        return AvailableDomain(
            prices = prices
        )
    }
}
