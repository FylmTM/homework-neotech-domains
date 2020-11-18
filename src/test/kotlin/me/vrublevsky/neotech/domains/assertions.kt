package me.vrublevsky.neotech.domains

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect
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

fun Cache.expectExists(key: String) = this.get(key).expect.notToBeNull()
fun Cache.expectNotExists(key: String) = this.get(key).expect.toBe(null)
