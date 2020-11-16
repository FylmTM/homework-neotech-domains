package me.vrublevsky.neotech.domains

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTest {

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @LocalServerPort
    protected var port: Int = 0

    lateinit var app: AppClient

    @BeforeEach
    fun setUp() {
        app = AppClient.create(port, objectMapper)
    }
}
