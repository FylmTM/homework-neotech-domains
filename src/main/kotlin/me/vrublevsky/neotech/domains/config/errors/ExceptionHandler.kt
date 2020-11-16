package me.vrublevsky.neotech.domains.config.errors

import me.vrublevsky.neotech.domains.common.api.ApiResponse
import me.vrublevsky.neotech.domains.common.api.error
import me.vrublevsky.neotech.domains.common.exceptions.AppException
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException
import javax.validation.ElementKind

private val logger = logger {}

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun appException(e: AppException): ResponseEntity<ApiResponse<ErrorResponse>> =
        ResponseEntity
            .status(e.httpStatus)
            .body(
                AppErrorResponse(
                    code = e.code,
                    message = e.message ?: ""
                ).error()
            )

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(e: ConstraintViolationException): ResponseEntity<ApiResponse<ErrorResponse>> {
        val violations = e.constraintViolations.map { violation ->
            BadRequestViolation(
                violation.propertyPath
                    .filter { node ->
                        when (node.kind) {
                            ElementKind.PROPERTY -> true
                            else -> false
                        }
                    }
                    .joinToString(".") { it.name },
                violation.message
            )
        }.toSet()

        return ResponseEntity
            .badRequest()
            .body(BadRequestErrorResponse(violations).error())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<ErrorResponse>> {
        val violations = e.bindingResult
            .fieldErrors
            .map {
                BadRequestViolation(
                    it.field,
                    it.defaultMessage ?: ""
                )
            }
            .toSet()

        return ResponseEntity
            .badRequest()
            .body(BadRequestErrorResponse(violations).error())
    }

    @ExceptionHandler
    fun internalServerError(e: Throwable): ResponseEntity<ApiResponse<ErrorResponse>> {
        logger.error("Internal Server Error", e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                InternalServerErrorResponse(
                    message = "${e.javaClass.name}: ${e.message}"
                ).error()
            )
    }
}
