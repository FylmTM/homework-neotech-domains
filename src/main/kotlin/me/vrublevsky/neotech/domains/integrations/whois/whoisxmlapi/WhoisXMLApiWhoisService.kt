package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.integrations.whois.DomainInformation
import me.vrublevsky.neotech.domains.integrations.whois.WhoisService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty(
    value = ["app.whois"],
    havingValue = "whoisxmlapi"
)
class WhoisXMLApiWhoisService(
    private val configuration: WhoisXMLApiWhoisServiceConfiguration
) : WhoisService {

    override fun getInformation(domain: Domain): DomainInformation? {
        TODO()
    }
}
