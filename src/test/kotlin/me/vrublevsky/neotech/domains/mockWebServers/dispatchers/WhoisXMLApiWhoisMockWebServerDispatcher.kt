package me.vrublevsky.neotech.domains.mockWebServers.dispatchers

import me.vrublevsky.neotech.domains.TestDomain
import me.vrublevsky.neotech.domains.mockWebServers.mockJsonResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object WhoisXMLApiWhoisMockWebServerDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        if (request.requestUrl?.encodedPath != "/whoisserver/WhoisService") {
            return mockJsonResponse(responseCode = 404)
        }

        return when (request.requestUrl?.queryParameter("domainName")) {
            TestDomain.notAvailable.normalized -> mockJsonResponse(
                resource = "responses/whoisxmlapi/google.com.json"
            )
            TestDomain.integrationError.normalized -> mockJsonResponse(
                resource = "responses/whoisxmlapi/error.json"
            )
            TestDomain.integrationInternalError.normalized -> mockJsonResponse(
                responseCode = 500
            )
            TestDomain.facebookCom.normalized -> mockJsonResponse(
                resource = "responses/whoisxmlapi/facebook.com.json"
            )
            TestDomain.inboxLv.normalized -> mockJsonResponse(
                resource = "responses/whoisxmlapi/inbox.lv.json"
            )
            null, "" -> mockJsonResponse(
                resource = "responses/whoisxmlapi/error_domain_name_missing.json"
            )
            else -> mockJsonResponse(
                resource = "responses/whoisxmlapi/non_existing_domain.json"
            )
        }
    }
}
