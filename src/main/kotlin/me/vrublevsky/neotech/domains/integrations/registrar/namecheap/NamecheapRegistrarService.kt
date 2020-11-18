package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.integrations.registrar.AvailableDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.DomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.MissingDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.NotAvailableDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.RegistrarService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty(
    value = ["app.integrations.registrar.namecheap.enabled"],
    havingValue = "true"
)
class NamecheapRegistrarService(
    private val operations: NamecheapRegistrarOperations
) : RegistrarService {

    override val name: String = "Namecheap"

    override fun getInformation(domain: Domain): DomainRegistrarInformation {
        val domainCheckResult = operations.checkDomain(domain.normalized)
            ?: return MissingDomainRegistrarInformation

        if (domainCheckResult.isAvailable && domainCheckResult.price != null) {
            return AvailableDomainRegistrarInformation(
                price = domainCheckResult.price
            )
        }

        if (!domainCheckResult.isAvailable) {
            return NotAvailableDomainRegistrarInformation
        }

        val domainPrices = operations.domainPrices()

        val tld = domain.resolveTLD(domainPrices.tldList)
            ?: return MissingDomainRegistrarInformation

        val price = domainPrices.prices[tld]
            ?: return MissingDomainRegistrarInformation

        return AvailableDomainRegistrarInformation(
            price = price
        )
    }
}
