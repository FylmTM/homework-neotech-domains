package me.vrublevsky.neotech.domains

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Feign
import feign.Logger
import feign.RequestLine
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import me.vrublevsky.neotech.domains.common.api.EmptyApiResponse

interface AppClient {

    @RequestLine("GET /api/v1")
    fun apiRoot(): EmptyApiResponse

    companion object {
        fun create(
            port: Int,
            objectMapper: ObjectMapper,
        ): AppClient {
            return Feign
                .builder()
                .encoder(JacksonEncoder(objectMapper))
                .decoder(JacksonDecoder(objectMapper))
                .logger(Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(AppClient::class.java, "http://localhost:$port")
        }
    }
}

