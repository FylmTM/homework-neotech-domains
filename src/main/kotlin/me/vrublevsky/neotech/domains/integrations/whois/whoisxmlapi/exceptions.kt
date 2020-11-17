package me.vrublevsky.neotech.domains.integrations.whois.whoisxmlapi

import me.vrublevsky.neotech.domains.common.exceptions.AppException
import me.vrublevsky.neotech.domains.common.errors.ErrorCode
import org.springframework.http.HttpStatus

class WhoisXMLApiErrorAppException(error: ErrorMessage) : AppException(
    httpStatus = HttpStatus.BAD_GATEWAY,
    code = ErrorCode.integrationError,
    message = "Request to WhoisXMLApi API resulted in error[$error]"
)

class WhoisXMLApiNoRecordErrorAppException : AppException(
    httpStatus = HttpStatus.BAD_GATEWAY,
    code = ErrorCode.integrationError,
    message = "Request to WhoisXMLApi API is successful, but does not contain record data"
)
