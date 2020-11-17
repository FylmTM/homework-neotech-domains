package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Feign
import feign.Logger
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import me.vrublevsky.neotech.domains.common.http.IntegrationErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface WhoisXMLApiWhoisServiceClient {

    @RequestLine("GET /whoisserver/WhoisService?domainName={domainName}")
    fun get(@Param("domainName") domainName: String): WhoisResponse
}

@Configuration
class WhoisXMLApiWhoisServiceClientConfiguration {

    @Bean
    fun whoisXMLApiWhoisServiceClient(
        configuration: WhoisXMLApiWhoisServiceConfiguration,
        objectMapper: ObjectMapper,
    ): WhoisXMLApiWhoisServiceClient {
        return Feign
            .builder()
            .encoder(JacksonEncoder(objectMapper))
            .decoder(JacksonDecoder(objectMapper))
            .errorDecoder(IntegrationErrorDecoder("whoisxmlapi"))
            .logger(Slf4jLogger())
            .logLevel(if(configuration.debug) Logger.Level.FULL else Logger.Level.BASIC)
            .requestInterceptor {
                it.query("apiKey", configuration.apiKey)
            }
            .target(WhoisXMLApiWhoisServiceClient::class.java, configuration.url)
    }
}
