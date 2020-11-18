package me.vrublevsky.neotech.domains

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect
import me.vrublevsky.neotech.domains.common.api.ApiResponse
import me.vrublevsky.neotech.domains.common.api.ApiResponseStatus
import org.springframework.cache.Cache

val <T : Any?> T.expect
    get() = expect(this)

fun <T : Any?> T.expectAll(assertions: Expect<T>.() -> Unit): Expect<T> = expect(this, assertions)

fun <T> parametrized(
    vararg source: T,
    block: Expect<String>.(data: T) -> Unit,
) {
    expect("parametrized") {
        source.forEach {
            block.invoke(this, it)
        }
    }
}

fun <T> Expect<ApiResponse<T>>.toBeOk(payload: T) =
    addAssertionsCreatedBy {
        feature(ApiResponse<T>::status).toBe(ApiResponseStatus.OK)
        feature(ApiResponse<T>::payload).toBe(payload)
    }

fun Expect<Cache>.exists(key: String) =
    feature("cache for key $key exists") { get(key) }.notToBeNull()

fun Expect<Cache>.notExists(key: String) =
    feature("cache for key $key not exists") { get(key) }.toBe(null)
