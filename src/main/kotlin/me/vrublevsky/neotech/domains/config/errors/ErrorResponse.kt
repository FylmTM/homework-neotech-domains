package me.vrublevsky.neotech.domains.config.errors

interface ErrorResponse {
    val code: String
    val message: String
}

data class AppErrorResponse(
    override val code: String,
    override val message: String
) : ErrorResponse

data class InternalServerErrorResponse(
    override val message: String,
) : ErrorResponse {
    override val code: String = ErrorCode.internalServerError
}

data class BadRequestViolation(
    val property: String,
    val message: String,
)

data class BadRequestErrorResponse(
    val violations: Set<BadRequestViolation>,
) : ErrorResponse {
    override val message: String = "Bad request"
    override val code: String = ErrorCode.badRequest
}
