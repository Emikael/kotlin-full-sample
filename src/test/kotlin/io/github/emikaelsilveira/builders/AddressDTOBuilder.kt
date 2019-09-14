package io.github.emikaelsilveira.builders

import io.github.emikaelsilveira.domain.entities.AddressDTO
import org.joda.time.LocalDateTime

class AddressDTOBuilder {

    var id = 1L
    var cep = "88708-001"
    var street = "São João Street"
    var complement = "São João Club Residential"
    var neighborhood = "São João Margem Esquerda"
    var city = "Tubarão"
    var state = "SC"
    var ibge = 1238127L
    var createAt: LocalDateTime? = null
    var updateAt: LocalDateTime? = null

    private fun build() = AddressDTO(
        id = id,
        cep = cep,
        street = street,
        complement = complement,
        neighborhood = neighborhood,
        city = city,
        state = state,
        ibge = ibge,
        createAt = createAt,
        updateAt = updateAt
    )

    companion object {
        fun build(block: (AddressDTOBuilder.() -> Unit)? = null) = when (block) {
            null -> AddressDTOBuilder().build()
            else -> AddressDTOBuilder().apply(block).build()
        }
    }
}
