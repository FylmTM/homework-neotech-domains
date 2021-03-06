package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.TestDomain
import me.vrublevsky.neotech.domains.common.exceptions.AppException
import me.vrublevsky.neotech.domains.common.http.IntegrationRequestFailedAppException
import me.vrublevsky.neotech.domains.common.errors.ErrorCode
import me.vrublevsky.neotech.domains.expect
import me.vrublevsky.neotech.domains.integrations.whois.DomainWhoisInformation
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime

class WhoisXMLApiWhoisServiceTest : IntegrationTest() {

    @Autowired
    lateinit var service: WhoisXMLApiWhoisService

    @Test
    fun `retrieve information for existing domain`() {
        service
            .getInformation(TestDomain.notAvailable)
            .expect
            .toBe(DomainWhoisInformation(
                registrar = "MarkMonitor, Inc.",
                expirationDate = OffsetDateTime.parse("2028-09-13T07:00Z")
            ))
    }

    @Test
    fun `retrieve information for facebook`() {
        service
            .getInformation(TestDomain.facebookCom)
            .expect
            .toBe(DomainWhoisInformation(
                registrar = "RegistrarSafe, LLC",
                expirationDate = OffsetDateTime.parse("2028-03-30T04:00Z")
            ))
    }

    @Test
    fun `retrieve information for inbox`() {
        service
            .getInformation(TestDomain.inboxLv)
            .expect
            .toBe(DomainWhoisInformation(
                registrar = null,
                expirationDate = null
            ))
    }

    @Test
    fun `retrieve information for non-existing domain`() {
        service
            .getInformation(TestDomain.available)
            .expect
            .toBe(null)
    }

    @Test
    fun `retrieve results in error`() {
        expect {
            service.getInformation(TestDomain.integrationError)
        }.toThrow<WhoisXMLApiErrorAppException> {
            feature(AppException::code).toBe(ErrorCode.integrationError)
        }
    }

    @Test
    fun `retrieve results in internal error`() {
        expect {
            service.getInformation(TestDomain.integrationInternalError)
        }.toThrow<IntegrationRequestFailedAppException> {
            feature(AppException::code).toBe(ErrorCode.integrationRequestFailed)
        }
    }
}
