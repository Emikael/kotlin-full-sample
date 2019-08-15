package io.github.emikaelsilveira.domain.entities

import java.time.LocalDateTime

data class UserDTO(
    val id: Long?,
    val name: String,
    val email: String,
    val phone: String?,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
)
