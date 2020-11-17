package me.vrublevsky.neotech.domains

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class NeotechDomainsApplication

fun main(args: Array<String>) {
    runApplication<NeotechDomainsApplication>(*args)
}
