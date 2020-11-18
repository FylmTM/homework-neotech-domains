package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import java.math.BigDecimal
import java.util.*

enum class NamecheapApiResponseStatus {
    OK, ERROR
}

// TODO: Find way to avoid duplication XML configuration for these fields
interface NamecheapApiResponse {
    val status: NamecheapApiResponseStatus
    val errors: List<NamecheapApiError>
}

@JacksonXmlRootElement(localName = "Error")
data class NamecheapApiError(
    @JacksonXmlProperty(localName = "Number", isAttribute = true)
    val number: Int,
) {
    // https://github.com/FasterXML/jackson-dataformat-xml/issues/423
    @JacksonXmlText
    lateinit var value: String
}

@JacksonXmlRootElement(localName = "ApiResponse")
data class NamecheapDomainsCheck(
    @JacksonXmlProperty(localName = "Status", isAttribute = true)
    override val status: NamecheapApiResponseStatus,
    @JacksonXmlProperty(localName = "Errors")
    override val errors: List<NamecheapApiError>,
    @JacksonXmlProperty(localName = "CommandResponse")
    val commandResponse: List<NamecheapDomainCheckResult>,
) : NamecheapApiResponse

@JacksonXmlRootElement(localName = "DomainCheckResult")
data class NamecheapDomainCheckResult(
    @JacksonXmlProperty(localName = "Domain", isAttribute = true)
    val domain: String,
    @JacksonXmlProperty(localName = "Available", isAttribute = true)
    val available: Boolean,
    @JacksonXmlProperty(localName = "IsPremiumName", isAttribute = true)
    val isPremiumName: Boolean,
    @JacksonXmlProperty(localName = "PremiumRegistrationPrice", isAttribute = true)
    val premiumRegistrationPrice: BigDecimal,
)

data class NamecheapUsersGetPricing(
    @JacksonXmlProperty(localName = "Status", isAttribute = true)
    override val status: NamecheapApiResponseStatus,
    @JacksonXmlProperty(localName = "Errors")
    override val errors: List<NamecheapApiError>,
    @JacksonXmlProperty(localName = "CommandResponse")
    @JacksonXmlElementWrapper(useWrapping = false)
    val commandResponse: NamecheapUsersGetPricingCommandResponse?,
) : NamecheapApiResponse

data class NamecheapUsersGetPricingCommandResponse(
    @JacksonXmlProperty(localName = "UserGetPricingResult")
    val productTypes: List<NamecheapUsersGetPricingProductType>,
)

enum class NamecheapUsersGetPricingProductTypeName {
    @JsonProperty("domains")
    DOMAINS
}

@JacksonXmlRootElement(localName = "ProductType")
data class NamecheapUsersGetPricingProductType(
    @JacksonXmlProperty(localName = "Name", isAttribute = true)
    val name: NamecheapUsersGetPricingProductTypeName,
    @JacksonXmlProperty(localName = "ProductCategory")
    @JacksonXmlElementWrapper(useWrapping = false)
    val productCategories: List<NamecheapUsersGetPricingProductCategory>
)

enum class NamecheapUsersGetPricingProductCategoryName {
    @JsonProperty("register")
    REGISTER
}

data class NamecheapUsersGetPricingProductCategory(
    @JacksonXmlProperty(localName = "Name", isAttribute = true)
    val name: NamecheapUsersGetPricingProductCategoryName,
    @JacksonXmlProperty(localName = "Product")
    @JacksonXmlElementWrapper(useWrapping = false)
    val products: List<NamecheapUsersGetPricingProduct>,
)

data class NamecheapUsersGetPricingProduct(
    @JacksonXmlProperty(localName = "Name", isAttribute = true)
    val name: String,
    @JacksonXmlProperty(localName = "Price")
    @JacksonXmlElementWrapper(useWrapping = false)
    val prices: List<NamecheapUsersGetPricingProductPrice>,
)

enum class NamecheapUsersGetPricingProductPriceDuration {
    YEAR
}

data class NamecheapUsersGetPricingProductPrice(
    @JacksonXmlProperty(localName = "Duration", isAttribute = true)
    val duration: Int,
    @JacksonXmlProperty(localName = "DurationType", isAttribute = true)
    val durationType: NamecheapUsersGetPricingProductPriceDuration,
    @JacksonXmlProperty(localName = "YourPrice", isAttribute = true)
    val yourPrice: BigDecimal,
    @JacksonXmlProperty(localName = "Currency", isAttribute = true)
    val currency: Currency,
)
