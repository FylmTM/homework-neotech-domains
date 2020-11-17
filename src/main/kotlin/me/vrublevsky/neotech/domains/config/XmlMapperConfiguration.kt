package me.vrublevsky.neotech.domains.config

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder




@Configuration
class XmlMapperConfiguration {

    @Bean
    fun xmlMapper(): XmlMapper {
        val builder = Jackson2ObjectMapperBuilder()
        val xmlMapper: XmlMapper = builder
            .createXmlMapper(true)
            .build()

        xmlMapper.configOverride(List::class.java).apply {
            setterInfo = JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY)
        }

        return xmlMapper
    }
}
