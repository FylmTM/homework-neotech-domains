package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import feign.Feign
import feign.Logger
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import me.vrublevsky.neotech.domains.common.http.IntegrationErrorDecoder
import me.vrublevsky.neotech.domains.config.XmlMapperHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * https://www.namecheap.com/support/api/methods/
 */
interface NamecheapRegistrarServiceClient {

    /**
     * https://www.namecheap.com/support/api/methods/domains/check/
     */
    @RequestLine("GET /xml.response?Command=namecheap.domains.check&DomainList={domains}")
    fun domainsCheck(@Param("domains") domains: String): NamecheapDomainsCheck

    @RequestLine("GET /xml.response?Command=namecheap.users.getPricing&ProductType=DOMAIN&ActionName=REGISTER")
    fun getDomainRegisterPricing(): NamecheapUsersGetPricing
}

@Configuration
class NamecheapRegistrarServiceClientConfiguration {

    @Bean
    fun namecheapRegistrarServiceClient(
        configuration: NamecheapRegistrarServiceConfiguration,
        xmlMapper: XmlMapperHolder,
    ): NamecheapRegistrarServiceClient {
        return Feign.builder()
            .encoder(JacksonEncoder(xmlMapper.value))
            .decoder(JacksonDecoder(xmlMapper.value))
            .errorDecoder(IntegrationErrorDecoder("namecheap"))
            .logger(Slf4jLogger())
            .logLevel(
                if (configuration.debug) Logger.Level.FULL
                else Logger.Level.BASIC
            )
            .requestInterceptor {
                it.query("ApiUser", configuration.apiUser)
                it.query("ApiKey", configuration.apiKey)
                it.query("UserName", configuration.apiUsername)
                it.query("ClientIp", configuration.apiClientIp)
            }
            .target(NamecheapRegistrarServiceClient::class.java, configuration.url)
    }
}
