package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.TestDomain
import me.vrublevsky.neotech.domains.common.errors.ErrorCode
import me.vrublevsky.neotech.domains.common.exceptions.AppException
import me.vrublevsky.neotech.domains.common.http.IntegrationRequestFailedAppException
import me.vrublevsky.neotech.domains.exists
import me.vrublevsky.neotech.domains.expect
import me.vrublevsky.neotech.domains.expectAll
import me.vrublevsky.neotech.domains.notExists
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NamecheapRegistrarOperationsTest : IntegrationTest() {

    @Autowired
    lateinit var operations: NamecheapRegistrarOperations

    @Test
    fun `check domain for not available domain`() {
        cache(namecheapDomainCheckCache)
            .expect.notExists(TestDomain.notAvailable.normalized)

        operations.checkDomain(TestDomain.notAvailable.normalized)
            .expect.toBe(
                CheckDomainResult(
                    isAvailable = false,
                    price = null
                )
            )

        cache(namecheapDomainCheckCache)
            .expect.exists(TestDomain.notAvailable.normalized)
    }

    @Test
    fun `check domain for available domain`() {
        cache(namecheapDomainCheckCache)
            .expect.notExists(TestDomain.available.normalized)

        operations.checkDomain(TestDomain.available.normalized)
            .expect.toBe(
                CheckDomainResult(
                    isAvailable = true,
                    price = null
                )
            )

        cache(namecheapDomainCheckCache)
            .expect.exists(TestDomain.available.normalized)
    }

    @Test
    fun `check domain results in error when domain list not found`() {
        cache(namecheapDomainCheckCache)
            .expect.notExists(TestDomain.integrationError.normalized)

        operations.checkDomain(TestDomain.integrationError.normalized)
            .expect.toBe(null)

        cache(namecheapDomainCheckCache)
            .expect.exists(TestDomain.integrationError.normalized)
    }

    @Test
    fun `check domain results in error for invalid tld`() {
        cache(namecheapDomainCheckCache)
            .expect.notExists(TestDomain.invalidTld.normalized)

        operations.checkDomain(TestDomain.invalidTld.normalized)
            .expect.toBe(null)

        cache(namecheapDomainCheckCache)
            .expect.exists(TestDomain.invalidTld.normalized)
    }

    @Test
    fun `check domain result in internal error`() {
        cache(namecheapDomainCheckCache)
            .expect.notExists(TestDomain.integrationInternalError.normalized)

        expect {
            operations.checkDomain(TestDomain.integrationInternalError.normalized)
        }.toThrow<IntegrationRequestFailedAppException> {
            feature(AppException::code).toBe(ErrorCode.integrationRequestFailed)
        }

        cache(namecheapDomainCheckCache)
            .expect.notExists(TestDomain.integrationInternalError.normalized)
    }

    @Test
    fun `get domain prices`() {
        cache(namecheapDomainPricesCache)
            .expect.notExists("domainPrices")

        val domainPrices = operations.domainPrices()
        domainPrices.expectAll {
            feature(DomainPrices::tldList).isNotEmpty()
            feature(DomainPrices::prices).isNotEmpty()
            feature("prices have all tlds") { prices.keys }
                .containsElementsOf(domainPrices.tldList)
        }

        cache(namecheapDomainPricesCache)
            .expect.exists("domainPrices")
    }
}
