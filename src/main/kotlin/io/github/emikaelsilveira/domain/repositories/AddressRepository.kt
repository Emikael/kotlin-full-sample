package io.github.emikaelsilveira.domain.repositories

import io.github.emikaelsilveira.domain.entities.AddressDTO

interface AddressRepository {

    fun getByCep(cep: String): AddressDTO?

    fun create(addressDTO: AddressDTO): AddressDTO

    fun update(id: Long, addressDTO: AddressDTO): AddressDTO

    fun getAll(): List<AddressDTO>
}
