package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import me.vrublevsky.neotech.domains.domain.Domain
import me.vrublevsky.neotech.domains.integrations.whois.DomainInformation
import me.vrublevsky.neotech.domains.integrations.whois.WhoisService
import mu.KotlinLogging.logger
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

private val logger = logger {}

@Service
@ConditionalOnProperty(
    value = ["app.whois"],
    havingValue = "whoisxmlapi"
)
class WhoisXMLApiWhoisService(
    private val client: WhoisXMLApiWhoisServiceClient,
) : WhoisService {

    override fun getInformation(domain: Domain): DomainInformation? {
        logger.info { "Requesting whois information for $domain" }
        val response = client.get(domain.normalized)

        if (response.errorMessage != null) {
            throw WhoisXMLApiErrorAppException(response.errorMessage)
        }

        if (response.record == null) {
            throw WhoisXMLApiNoRecordErrorAppException()
        }

        // If response contains any data error,
        // consider information to be not available
        if (response.record.dataError != null) {
            return null
        }

        // If any of information we need is missing,
        // consider information to be not available
        if (response.record.expiresDate == null
            || response.record.registrarName == null) {
            return null
        }

        return DomainInformation(
            registrar = response.record.registrarName,
            expirationDate = response.record.expiresDate
        )
    }
}
