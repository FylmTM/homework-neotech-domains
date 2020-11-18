package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import me.vrublevsky.neotech.domains.common.extensions.toCurrency
import me.vrublevsky.neotech.domains.domain.DomainPrice
import me.vrublevsky.neotech.domains.domain.TLD
import me.vrublevsky.neotech.domains.integrations.registrar.namecheap.NamecheapUsersGetPricingProductCategoryName.REGISTER
import me.vrublevsky.neotech.domains.integrations.registrar.namecheap.NamecheapUsersGetPricingProductPriceDuration.YEAR
import me.vrublevsky.neotech.domains.integrations.registrar.namecheap.NamecheapUsersGetPricingProductTypeName.DOMAINS
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class NamecheapRegistrarOperations(
    private val client: NamecheapRegistrarServiceClient,
) {

    @Cacheable(namecheapDomainCheckCache)
    fun checkDomain(domainName: String): CheckDomainResult? {
        val result = client.domainsCheck(domainName)
        if (result.status == NamecheapApiResponseStatus.ERROR) {
            throw NamecheapErrorAppException(result.errors)
        }

        val domain = result.commandResponse.find { it.domain == domainName }
            ?: return null

        val price = if (domain.isPremiumName) {
            DomainPrice(
                amount = domain.premiumRegistrationPrice,
                // Namecheap does not include currency in domain check,
                // so we are assuming it to be USD
                currency = "USD".toCurrency()
            )
        } else null

        return CheckDomainResult(
            isAvailable = domain.available,
            price = price
        )
    }

    @Cacheable(namecheapDomainPricesCache)
    fun domainPrices(): DomainPrices {
        val result = client.getDomainRegisterPricing()

        if (result.status == NamecheapApiResponseStatus.ERROR) {
            throw NamecheapErrorAppException(result.errors)
        }

        if (result.commandResponse == null) {
            throw NamecheapBadResponseAppException("domain prices do not contain data")
        }

        val products = result
            .commandResponse
            .productTypes
            .filter { it.name == DOMAINS }
            .flatMap { it.productCategories }
            .filter { it.name == REGISTER }
            .flatMap { it.products }

        if (products.isEmpty()) {
            throw NamecheapBadResponseAppException("domain products do not contain products")
        }

        val tldList = products.map { it.name }.toSet()
        val prices = products
            .mapNotNull { product ->
                val price = product.prices
                    .find { it.duration == 1 && it.durationType == YEAR }
                    ?: product.prices.firstOrNull()
                price
                    ?.let { DomainPrice(amount = it.yourPrice, currency = it.currency) }
                    ?.let { product.name to it}
            }
            .toMap()

        return DomainPrices(
            tldList = tldList,
            prices = prices,
        )
    }
}

data class CheckDomainResult(
    val isAvailable: Boolean,
    val price: DomainPrice?,
)

data class DomainPrices(
    val tldList: Set<TLD>,
    val prices: Map<TLD, DomainPrice>,
)
