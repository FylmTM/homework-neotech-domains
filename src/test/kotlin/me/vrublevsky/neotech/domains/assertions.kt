package me.vrublevsky.neotech.domains

import ch.tutteli.atrium.api.verbs.expect

val <T : Any?> T.expect
    get() = expect(this)
