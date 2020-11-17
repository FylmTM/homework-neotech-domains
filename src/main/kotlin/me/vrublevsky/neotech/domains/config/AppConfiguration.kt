package me.vrublevsky.neotech.domains.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

enum class WhoisServiceType {
    WhoisXMLApi
}

@ConstructorBinding
@ConfigurationProperties("app")
data class AppConfiguration(
    val whois: WhoisServiceType,
)
