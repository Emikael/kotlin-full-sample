package io.github.emikaelsilveira.domain.entities

import org.joda.time.LocalDateTime

data class AddressDTO(
    val id: Long?,
    val cep: String,
    val street: String?,
    val complement: String?,
    val neighborhood: String?,
    val city: String?,
    val state: String?,
    val ibge: Long?,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)
