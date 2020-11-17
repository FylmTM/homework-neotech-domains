package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.expect
import me.vrublevsky.neotech.domains.mockWebServers.getResource
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NamecheapRegistrarServiceResponseSerializationTest : IntegrationTest() {

    @Autowired
    lateinit var xmlMapper: XmlMapper

    @Test
    fun `deserialize domains check response`() {
        val source = getResource("responses/namecheap/namecheap.domains.check.successful.xml")
        xmlMapper.readValue<NamecheapDomainsCheck>(source)
            .expect
            .toBe(NamecheapDomainsCheck(
                status = NamecheapApiResponseStatus.OK,
                errors = emptyList(),
                commandResponse = listOf(
                    NamecheapDomainCheckResult(
                        domain = "domain1.com",
                        available = false,
                        isPremiumName = false,
                        premiumRegistrationPrice = "0".toBigDecimal()
                    ),
                    NamecheapDomainCheckResult(
                        domain = "domain2.com",
                        available = false,
                        isPremiumName = false,
                        premiumRegistrationPrice = "0".toBigDecimal()
                    )
                )
            ))
    }

    @Test
    fun `deserialize domains check response with error`() {
        val source = getResource("responses/namecheap/namecheap.domains.check.error.xml")
        xmlMapper.readValue<NamecheapDomainsCheck>(source)
            .expect
            .toBe(NamecheapDomainsCheck(
                status = NamecheapApiResponseStatus.ERROR,
                errors = listOf(
                    NamecheapApiError(
                        number = 2010169,
                    ).apply {
                        value = "Parameter DomainList is Missing"
                    }
                ),
                commandResponse = listOf()
            ))
    }

    @Test
    fun `deserialize users getPricing response`() {
        val source = getResource("responses/namecheap/namecheap.users.getPricing.successful.xml")
        expect {
            xmlMapper.readValue<NamecheapUsersGetPricing>(source)
        }.notToThrow()
    }
}
