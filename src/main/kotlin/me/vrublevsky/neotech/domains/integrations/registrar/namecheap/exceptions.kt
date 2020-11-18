package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import me.vrublevsky.neotech.domains.common.errors.ErrorCode
import me.vrublevsky.neotech.domains.common.exceptions.AppException
import org.springframework.http.HttpStatus

class NamecheapErrorAppException(errors: List<NamecheapApiError>) : AppException(
    httpStatus = HttpStatus.BAD_GATEWAY,
    code = ErrorCode.integrationError,
    message = "Request to Namecheap API resulted in error: $errors"
)

class NamecheapBadResponseAppException(message: String) : AppException(
    httpStatus = HttpStatus.BAD_GATEWAY,
    code = ErrorCode.integrationError,
    message = "Response from Namecheap API is malformed: $message"
)
