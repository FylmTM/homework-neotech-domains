package me.vrublevsky.neotech.domains

import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect

val <T : Any?> T.expect
    get() = expect(this)

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
