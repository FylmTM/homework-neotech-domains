package me.vrublevsky.neotech.domains.api.v1.domain

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import me.vrublevsky.neotech.domains.AppClientException
import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.TestDomain
import me.vrublevsky.neotech.domains.common.data.Currencies
import me.vrublevsky.neotech.domains.domain.DomainPrice
import me.vrublevsky.neotech.domains.expect
import me.vrublevsky.neotech.domains.toBeOk
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.BAD_GATEWAY
import java.time.OffsetDateTime

class DomainControllerTest : IntegrationTest() {

    @Test
    fun `get status - not available`() {
        app.domainStatus(TestDomain.notAvailable.original)
            .expect
            .toBeOk(
                DomainStatusResponse(
                    domain = TestDomain.notAvailable,
                    status = RegisteredDomain(
                        registrar = "MarkMonitor, Inc.",
                        expirationDate = OffsetDateTime.parse("2028-09-13T07:00Z")
                    )

                )
            )
    }

    @Test
    fun `get status - available`() {
        app.domainStatus(TestDomain.available.original)
            .expect
            .toBeOk(
                DomainStatusResponse(
                    domain = TestDomain.available,
                    status = AvailableDomain(
                        prices = listOf(
                            RegistrarDomainPrice(
                                registrar = "Namecheap",
                                available = true,
                                price = DomainPrice(
                                    amount = "16.00".toBigDecimal(),
                                    currency = Currencies.USD
                                )
                            )
                        )
                    )

                )
            )
    }

    @Test
    fun `get status - invalid tld`() {
        app.domainStatus(TestDomain.invalidTld.original)
            .expect
            .toBeOk(
                DomainStatusResponse(
                    domain = TestDomain.invalidTld,
                    status = AvailableDomain(
                        prices = listOf(
                            RegistrarDomainPrice(
                                registrar = "Namecheap",
                                available = false,
                                price = null
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun `get status - error`() {
        expect {
            app.domainStatus(TestDomain.integrationError.original)
        }.toThrow<AppClientException> {
            feature(AppClientException::httpStatus).toBe(BAD_GATEWAY.value())
        }
    }

    @Test
    fun `get status - internal error`() {
        expect {
            app.domainStatus(TestDomain.integrationInternalError.original)
        }.toThrow<AppClientException> {
            feature(AppClientException::httpStatus).toBe(BAD_GATEWAY.value())
        }
    }
}
