package me.vrublevsky.neotech.domains.api.v1

import ch.tutteli.atrium.api.fluent.en_GB.*
import me.vrublevsky.neotech.domains.IntegrationTest
import me.vrublevsky.neotech.domains.common.api.emptyApiResponse
import me.vrublevsky.neotech.domains.expect
import org.junit.jupiter.api.Test

class ApiRootControllerTest : IntegrationTest() {

    @Test
    fun `api accessible`() {
        app.apiRoot().expect.toBe(emptyApiResponse)
    }
}

