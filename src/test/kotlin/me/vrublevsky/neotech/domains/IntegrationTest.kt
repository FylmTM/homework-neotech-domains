package me.vrublevsky.neotech.domains

import com.fasterxml.jackson.databind.ObjectMapper
import me.vrublevsky.neotech.domains.mockWebServers.setupMockServers
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var cacheManager: CacheManager

    lateinit var app: AppClient

    @BeforeEach
    fun setUp() {
        setupMockServers()

        app = AppClient.create(port, objectMapper)

        // Clear cache before each test
        cacheManager.cacheNames
            .map { cacheManager.getCache(it)!! }
            .forEach(Cache::clear)
    }

    fun cache(name: String) = cacheManager.getCache(name)!!
}
