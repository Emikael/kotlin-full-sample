package io.github.emikaelsilveira.application.config.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.joda.time.LocalDateTime

class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    companion object {
        private const val DATETIME_FORMATTER = "dd/MM/YYYY HH:mm:ss"
    }

    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, provider: SerializerProvider?) {
        gen?.writeString(value?.toString(DATETIME_FORMATTER))
    }

}
