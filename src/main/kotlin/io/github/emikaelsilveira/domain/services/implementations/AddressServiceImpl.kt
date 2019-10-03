package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.entities.Address
import io.github.emikaelsilveira.domain.exceptions.NotFoundException
import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.domain.services.AddressService

class AddressServiceImpl(
    private val addressProvider: AddressProvider,
    private val repository: AddressRepository
) : AddressService {

    override fun getByCep(cep: String) = this.addressProvider.getAddress(cep).toDomain()

    override fun createOrUpdate(addressParameter: Address): Address {
        return when (val address = this.repository.getByCep(addressParameter.cep)) {
            null -> this.repository.create(addressParameter)
            else -> address.id?.let { this.repository.update(it, address) } ?: throw NotFoundException(address.cep)
        }
    }

    override fun getAll() = this.repository.getAll()
}
