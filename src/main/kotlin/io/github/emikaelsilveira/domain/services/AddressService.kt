package io.github.emikaelsilveira.domain.services

import io.github.emikaelsilveira.domain.entities.AddressDTO

interface AddressService {

    fun getByCep(cep: String): AddressDTO

    fun create(addressDTO: AddressDTO): AddressDTO

    fun update(id: Long, addressDTO: AddressDTO): AddressDTO
}
