package me.vrublevsky.neotech.domains.common.api

enum class ApiResponseStatus {
    OK, ERROR
}

data class ApiResponse<out T>(val status: ApiResponseStatus, val payload: T?)

fun <T> T.ok() = ApiResponse(ApiResponseStatus.OK, this)

fun <T> T.error() = ApiResponse(ApiResponseStatus.ERROR, this)

typealias EmptyApiResponse = ApiResponse<Unit>

val emptyApiResponse: EmptyApiResponse = ApiResponse(ApiResponseStatus.OK, null)
