package me.vrublevsky.neotech.domains.common.validations

import me.vrublevsky.neotech.domains.common.extensions.normalizeDomainName
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DomainValidator::class])
annotation class Domain(
    val message: String = "invalid domain name",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

private val domainRegexp = "^([^.]{1,63}\\.)?[^.]{1,63}\\.[^.]{2,63}$".toRegex()

class DomainValidator : ConstraintValidator<Domain, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        isValidDomain(value)
}

/**
 * Check that:
 * - Value is not null
 * - Value consistent of 2 or 3 labels
 * - Last label contains at least 2 characters
 * - Each label is no longer than 63 characters
 * - Verifies that we can successfully normalize to punycode
 */
fun isValidDomain(value: String?): Boolean {
    if (value == null) {
        return false
    }

    if (!domainRegexp.matches(value)) {
        return false
    }

    return try {
        // Throws exception if any label is longer than 63 octets after translating to punycode.
        // Or just invalid data.
        value.normalizeDomainName()
        true
    } catch (_: Throwable) {
        false
    }
}
