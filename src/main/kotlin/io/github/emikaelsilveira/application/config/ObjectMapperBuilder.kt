package io.github.emikaelsilveira.application.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.emikaelsilveira.application.config.serializers.LocalDateTimeSerializer
import org.joda.time.LocalDateTime

object ObjectMapperBuilder {

    private val objectMapper = with(jacksonObjectMapper()) {
        this.registerKotlinModule()
        this.registerModule(createDateModule())
    }

    private fun createDateModule(): SimpleModule = with(SimpleModule()) {
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
    }

    fun configJsonMapper(): ObjectMapper = this.objectMapper
}

fun ObjectMapper.useSnakeCase(): ObjectMapper = this.setPropertyNamingStrategy(SNAKE_CASE)

fun ObjectMapper.includeNonNull(): ObjectMapper = this.setSerializationInclusion(JsonInclude.Include.NON_NULL)

val objectMapper = ObjectMapperBuilder
    .configJsonMapper()
    .useSnakeCase()
    .includeNonNull()
