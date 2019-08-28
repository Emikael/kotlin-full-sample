package io.github.emikaelsilveira.domain.services

import io.github.emikaelsilveira.domain.entities.AddressDTO

interface AddressService {

    fun getByCep(cep: String): AddressDTO

    fun createOrUpdate(addressDTO: AddressDTO): AddressDTO

    fun getAll(): List<AddressDTO>

}
