package io.github.emikaelsilveira.domain.services

import io.github.emikaelsilveira.domain.entities.Address

interface AddressService {

    fun getByCep(cep: String): Address

    fun createOrUpdate(address: Address): Address

    fun getAll(): List<Address>

}
