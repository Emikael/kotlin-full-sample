package io.github.emikaelsilveira.domain.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.LocalDateTime

data class User(
    val id: Long?,
    val name: String,
    val email: String,
    val phone: String?,
    @get:JsonProperty("address")
    val address: Address?,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)
