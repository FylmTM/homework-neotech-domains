package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConstructorBinding
@ConfigurationProperties("app.integrations.registrar.namecheap")
data class NamecheapRegistrarServiceConfiguration(
    val enabled: Boolean,
    val debug: Boolean,
    val url: String,
    val apiKey: String,
    val apiUser: String,
    val apiUsername: String,
    val apiClientIp: String,
    /**
     * Defined in minutes.
     */
    @NestedConfigurationProperty
    val cacheDuration: NamecheapRegistrarServiceConfigurationCacheDuration,
)

@ConstructorBinding
data class NamecheapRegistrarServiceConfigurationCacheDuration(
    val domainCheck: Long,
    val domainPrices: Long,
)
