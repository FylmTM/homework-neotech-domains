package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import me.vrublevsky.neotech.domains.config.CacheExpirationSpec
import me.vrublevsky.neotech.domains.config.CacheSpec
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

const val namecheapDomainCheckCache = "namecheap/domainCheck"
const val namecheapDomainPricesCache = "namecheap/domainPrices"

@Configuration
class NamecheapRegistrarServiceCache {

    @Bean
    fun domainCheck(configuration: NamecheapRegistrarServiceConfiguration) = CacheSpec(
        name = namecheapDomainCheckCache,
        expiration = CacheExpirationSpec(
            duration = configuration.cacheDuration.domainCheck,
            timeUnit = TimeUnit.MINUTES
        )
    )

    @Bean
    fun domainPrices(configuration: NamecheapRegistrarServiceConfiguration) = CacheSpec(
        name = namecheapDomainPricesCache,
        expiration = CacheExpirationSpec(
            duration = configuration.cacheDuration.domainPrices,
            timeUnit = TimeUnit.MINUTES
        )
    )
}
