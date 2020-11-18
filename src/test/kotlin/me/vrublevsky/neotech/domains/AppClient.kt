package me.vrublevsky.neotech.domains

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Feign
import feign.Logger
import feign.Param
import feign.RequestLine
import feign.Response
import feign.codec.ErrorDecoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import me.vrublevsky.neotech.domains.api.v1.domain.DomainStatusResponse
import me.vrublevsky.neotech.domains.common.api.ApiResponse
import me.vrublevsky.neotech.domains.common.api.EmptyApiResponse
import java.lang.RuntimeException

interface AppClient {

    @RequestLine("GET /api/v1")
    fun apiRoot(): EmptyApiResponse

    @RequestLine("GET /api/v1/domain/{domainName}/status")
    fun domainStatus(@Param("domainName") domainName: String): ApiResponse<DomainStatusResponse>

    companion object {
        fun create(
            port: Int,
            objectMapper: ObjectMapper,
        ): AppClient {
            return Feign
                .builder()
                .encoder(JacksonEncoder(objectMapper))
                .decoder(JacksonDecoder(objectMapper))
                .errorDecoder { _, response ->
                    AppClientException(response.status())
                }
                .logger(Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(AppClient::class.java, "http://localhost:$port")
        }
    }
}

class AppClientException(val httpStatus: Int) : RuntimeException()
