package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.TestDomain
import me.vrublevsky.neotech.domains.common.data.Currencies
import me.vrublevsky.neotech.domains.common.errors.ErrorCode
import me.vrublevsky.neotech.domains.common.exceptions.AppException
import me.vrublevsky.neotech.domains.common.http.IntegrationRequestFailedAppException
import me.vrublevsky.neotech.domains.domain.DomainPrice
import me.vrublevsky.neotech.domains.expect
import me.vrublevsky.neotech.domains.integrations.registrar.AvailableDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.MissingDomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.NotAvailableDomainRegistrarInformation
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NamecheapRegistrarServiceTest : IntegrationTest() {

    @Autowired
    lateinit var service: NamecheapRegistrarService

    @Test
    fun `get for not available domain`() {
        service.getInformation(TestDomain.notAvailable)
            .expect.toBe(NotAvailableDomainRegistrarInformation)
    }

    @Test
    fun `get for available domain`() {
        service.getInformation(TestDomain.available)
            .expect.toBe(AvailableDomainRegistrarInformation(
                price = DomainPrice(
                    amount = "16.00".toBigDecimal(),
                    currency = Currencies.USD
                )
            ))
    }

    @Test
    fun `get for invalid tld`() {
        service.getInformation(TestDomain.invalidTld)
            .expect.toBe(MissingDomainRegistrarInformation)
    }

    @Test
    fun `get for error`() {
        service.getInformation(TestDomain.integrationError)
            .expect.toBe(MissingDomainRegistrarInformation)
    }

    @Test
    fun `get for internal error`() {
        expect {
            service.getInformation(TestDomain.integrationInternalError)
        }.toThrow<IntegrationRequestFailedAppException> {
            feature(AppException::code).toBe(ErrorCode.integrationRequestFailed)
        }
    }
}
