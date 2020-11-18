package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class WhoisResponse(
    @JsonProperty("WhoisRecord")
    val record: WhoisRecord?,

    @JsonProperty("ErrorMessage")
    val errorMessage: ErrorMessage?,
)

data class WhoisRecord(
    val dataError: String?,
    val registrarName: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val expiresDate: OffsetDateTime?,
)

data class ErrorMessage(
    val errorCode: String,
    val msg: String,
)
