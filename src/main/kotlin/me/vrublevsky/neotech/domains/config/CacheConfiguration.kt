package me.vrublevsky.neotech.domains.config

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Ticker
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfiguration {

    @Bean
    fun cacheManager(specs: List<CacheSpec>, ticker: Ticker): CacheManager {
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(
            specs.map { this.buildCache(it, ticker) }
        )
        return cacheManager
    }


    @Bean
    fun ticker(): Ticker = Ticker.systemTicker()

    private fun buildCache(spec: CacheSpec, ticker: Ticker): CaffeineCache {
        return CaffeineCache(
            spec.name,
            Caffeine.newBuilder()
                .ticker(ticker)
                .apply {
                    spec.expiration?.let {
                        this.expireAfterWrite(it.duration, it.timeUnit)
                    }
                }
                .build()
        )
    }
}

data class CacheSpec(
    val name: String,
    val expiration: CacheExpirationSpec? = null,
)

data class CacheExpirationSpec(
    val duration: Long,
    val timeUnit: TimeUnit,
)
