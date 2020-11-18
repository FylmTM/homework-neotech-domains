package me.vrublevsky.neotech.domains.mockWebServers

import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.mockWebServers.dispatchers.NamecheapMockWebServerDispatcher
import me.vrublevsky.neotech.domains.mockWebServers.dispatchers.WhoisXMLApiWhoisMockWebServerDispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

private var initialized = false
private const val whoisxmlapiPort = 10020
private const val namecheapPort = 10021

fun setupMockServers() {
    // Initialize all expectations only once
    if (initialized) {
        return
    }

    initialized = true

    val mockWebServers = listOf(
        // whoisxmlapi
        MockWebServer().apply {
            dispatcher = WhoisXMLApiWhoisMockWebServerDispatcher
            start(whoisxmlapiPort)
        },
        // namecheap
        MockWebServer().apply {
            dispatcher = NamecheapMockWebServerDispatcher
            start(namecheapPort)
        }
    )

    // Stop all mock servers on shutdown
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            mockWebServers.forEach(MockWebServer::shutdown)
        }
    })
}

fun mockJsonResponse(
    responseCode: Int = 200,
    body: String? = null,
    resource: String? = null,
): MockResponse =
    MockResponse().apply {
        setResponseCode(responseCode)
        addHeader("Content-Type", "application/json; charset=utf-8")
        setBody(body ?: resource?.let(::getResource) ?: "{}")
    }

fun mockXmlResponse(
    responseCode: Int = 200,
    body: String? = null,
    resource: String? = null,
): MockResponse =
    MockResponse().apply {
        setResponseCode(responseCode)
        addHeader("Content-Type", "text/xml; charset=utf-8")
        setBody(body ?: resource?.let(::getResource) ?: "{}")
    }

fun getResource(path: String): String =
    IntegrationTest::class.java.classLoader
        .getResource(path)
        .readText()
