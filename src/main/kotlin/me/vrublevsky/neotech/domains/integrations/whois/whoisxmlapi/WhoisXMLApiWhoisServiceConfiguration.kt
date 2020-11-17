package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("app.integrations.whois.whoisxmlapi")
data class WhoisXMLApiWhoisServiceConfiguration(
    val url: String,
    val apiKey: String,
)
