package me.vrublevsky.neotech.domains.common.errors

/**
 * Single-point of knowledge about all application error codes.
 *
 * [me.vrublevsky.neotech.domains.common.exceptions.AppException] requires code to be specified,
 * to ensure consistency across app.
 */
object ErrorCode {
    const val internalServerError = "internal_server_error"
    const val badRequest = "bad_request"
    const val integrationError = "integration_error"
    const val integrationRequestFailed = "integration_request_failed"
}
