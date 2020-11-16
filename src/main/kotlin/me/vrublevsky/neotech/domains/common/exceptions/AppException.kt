package me.vrublevsky.neotech.domains.common.exceptions

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

abstract class AppException(
    val httpStatus: HttpStatus,
    val code: String,
    message: String,
) : RuntimeException(message)
