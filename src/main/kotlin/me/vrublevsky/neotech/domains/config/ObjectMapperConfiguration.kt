package me.vrublevsky.neotech.domains.config

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import javax.annotation.PostConstruct

@Configuration
class ObjectMapperConfiguration(
    private val objectMapper: ObjectMapper
) {

    @PostConstruct
    fun configure() {
        objectMapper.configOverride(BigDecimal::class.java).apply {
            format = JsonFormat.Value.forShape(JsonFormat.Shape.STRING)
        }
    }
}
