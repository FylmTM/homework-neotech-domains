package me.vrublevsky.neotech.domains.mockWebServers.dispatchers

import me.vrublevsky.neotech.domains.TestDomain
import me.vrublevsky.neotech.domains.mockWebServers.mockXmlResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object NamecheapMockWebServerDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        if (request.requestUrl?.encodedPath != "/xml.response") {
            return mockXmlResponse(responseCode = 404)
        }

        val command = request.requestUrl?.queryParameter("Command")

        if (command == "namecheap.domains.check") {
            return when (request.requestUrl?.queryParameter("DomainList")) {
                TestDomain.google.normalized -> mockXmlResponse(
                    resource = "responses/namecheap/namecheap.domains.check.google.com.xml"
                )
                TestDomain.available.normalized -> mockXmlResponse(
                    resource = "responses/namecheap/namecheap.domains.check.inexisting-domain-for-testing.com.xml"
                )
                TestDomain.integrationInternalError.normalized -> mockXmlResponse(
                    responseCode = 500
                )
                null, "", TestDomain.integrationError.normalized -> mockXmlResponse(
                    resource = "responses/namecheap/namecheap.domains.check.error.xml"
                )
                else -> mockXmlResponse(
                    responseCode = 400
                )
            }
        }

        if (command == "namecheap.users.getPricing") {
            return mockXmlResponse(
                resource = "responses/namecheap/namecheap.users.getPricing.xml"
            )
        }

        return mockXmlResponse(responseCode = 404)
    }
}
