package io.github.emikaelsilveira.domain.repositories

import io.github.emikaelsilveira.domain.entities.Address

interface AddressRepository {

    fun getByCep(cep: String): Address?

    fun create(address: Address): Address

    fun update(id: Long, address: Address): Address

    fun getAll(): List<Address>
}
