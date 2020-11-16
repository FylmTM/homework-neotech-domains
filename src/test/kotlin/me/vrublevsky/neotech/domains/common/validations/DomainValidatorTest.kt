package me.vrublevsky.neotech.domains.common.validations

import ch.tutteli.atrium.api.fluent.en_GB.*
import me.vrublevsky.neotech.domains.parametrized
import org.junit.jupiter.api.Test

class DomainValidatorTest {

    @Test
    fun `test is valid domain name`() {
        parametrized(
            Arguments(
                input = "facebook.com",
                isValid = true,
            ),
            Arguments(
                input = "third.facebook.com",
                isValid = true,
            ),
            Arguments(
                input = "chinese.xn--3ds443g",
                isValid = true,
            ),
            Arguments(
                input = "http://президент.рф/",
                isValid = true,
            ),
            Arguments(
                input = "中国政府.政务.cn",
                isValid = true
            ),
            Arguments(
                input = "原來我不帥.cn",
                isValid = true,
            ),
            Arguments(
                // 63 octets in label
                input = "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.com",
                isValid = true,
            ),
            Arguments(
                // more than 3 labels
                input = "fourth.third.facebook.com",
                isValid = false,
            ),
            Arguments(
                // 64 octets in label
                input = "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.com",
                isValid = false,
            ),
            Arguments(
                // Top-level domain less than 2 characters
                input = "test.c",
                isValid = false,
            ),
            Arguments(
                // Label less than 63 characters, but will result in more than 63 octets in punycode
                input = "原來我不帥原來我不帥原來我不帥原來我不帥原來我不帥原來我不帥原來我不帥原來我不帥.cn",
                isValid = false,
            ),
        ) { (input, isValid) ->
            feature(
                description = "isValidDomain($input) == $isValid",
                provider = { isValidDomain(input) }
            ) {
                toBe(isValid)
            }
        }
    }

    data class Arguments(val input: String?, val isValid: Boolean)
}
