package io.github.emikaelsilveira.resources.viacep.entities

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.emikaelsilveira.domain.entities.AddressConvertable
import io.github.emikaelsilveira.domain.entities.Address

data class ViaCepAddress(
    @get:JsonProperty("cep")
    val cep: String,
    @get:JsonProperty("logradouro")
    val street: String,
    @get:JsonProperty("complemento")
    val complement: String,
    @get:JsonProperty("bairro")
    val neighborhood: String,
    @get:JsonProperty("localidade")
    val city: String,
    @get:JsonProperty("uf")
    val state: String,
    @get:JsonProperty("ibge")
    val ibge: Long
) : AddressConvertable {

    override fun toDomain() = Address(
        id = null,
        cep = this.cep,
        street = this.street,
        complement = this.complement,
        neighborhood = this.neighborhood,
        city = this.city,
        state = this.state,
        ibge = this.ibge,
        createAt = null,
        updateAt = null
    )
}
