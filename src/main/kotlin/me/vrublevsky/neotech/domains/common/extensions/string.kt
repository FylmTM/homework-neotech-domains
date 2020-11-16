package me.vrublevsky.neotech.domains.common.extensions

import java.net.IDN

/**
 * Convert unicode characters in domain name to punycode.
 */
fun String.normalizeDomainName(): String {
    return IDN.toASCII(this)
}
