package me.vrublevsky.neotech.domains.config

import ch.qos.logback.core.pattern.CompositeConverter
import kotlin.text.RegexOption.IGNORE_CASE

data class Pattern(
    val regex: Regex,
    val replaceWith: String,
)

private val patterns = listOf(
    Pattern(
        regex = "(apiKey)=([\\w\\d_-]+)".toRegex(IGNORE_CASE),
        replaceWith = "$1=***"
    )
)

fun convert(input: String): String =
    patterns.fold(input) { s, (regex, replaceWith) ->
        regex.replace(s, replaceWith)
    }

class LoggingMaskingConverter<E> : CompositeConverter<E>() {

    override fun transform(event: E, input: String): String =
        convert(input)
}

