package me.vrublevsky.neotech.domains.common.http

import feign.Response
import feign.codec.ErrorDecoder
import me.vrublevsky.neotech.domains.common.exceptions.AppException
import me.vrublevsky.neotech.domains.common.errors.ErrorCode
import org.springframework.http.HttpStatus
import java.lang.Exception

class IntegrationErrorDecoder(private val service: String) : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        return IntegrationRequestFailedAppException(
            message = "Service[$service] request failed with ${response.status()}"
        )
    }
}

class IntegrationRequestFailedAppException(message: String) : AppException(
    httpStatus = HttpStatus.BAD_GATEWAY,
    code = ErrorCode.integrationRequestFailed,
    message = message
)
