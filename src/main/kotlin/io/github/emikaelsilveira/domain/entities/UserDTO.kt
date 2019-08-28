package io.github.emikaelsilveira.domain.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.LocalDateTime

data class UserDTO(
    val id: Long?,
    val name: String,
    val email: String,
    val phone: String?,
    @get:JsonProperty("address")
    val addressDTO: AddressDTO?,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)
