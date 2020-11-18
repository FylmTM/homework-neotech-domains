package me.vrublevsky.neotech.domains.domain

import java.math.BigDecimal
import java.util.*

typealias TLD = String

data class Domain(
    val original: String,
    val normalized: String,
) {
    private val labels = normalized.split(".")
    private val topLevelTLD = labels.last()
    private val secondLevelTLD = labels.takeLast(2).joinToString(".")

    /**
     * Resolve TLD.
     * First try second-level, as it is more specific,
     * then try top-level.
     */
    fun resolveTLD(tldList: Set<TLD>): TLD? {
        if (tldList.contains(secondLevelTLD)) {
            return secondLevelTLD
        }
        if (tldList.contains(topLevelTLD)) {
            return topLevelTLD
        }
        return null
    }
}

data class DomainPrice(
    val amount: BigDecimal,
    val currency: Currency,
)
