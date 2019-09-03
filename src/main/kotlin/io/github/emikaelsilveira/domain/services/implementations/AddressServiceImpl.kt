package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.exceptions.NotFoundException
import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.domain.services.AddressService

class AddressServiceImpl(
    private val addressProvider: AddressProvider,
    private val repository: AddressRepository
) : AddressService {

    override fun getByCep(cep: String) = this.addressProvider.getAddress(cep).toDomain()

    override fun createOrUpdate(addressDTO: AddressDTO): AddressDTO {
        return when (val address = this.repository.getByCep(addressDTO.cep)) {
            null -> this.repository.create(addressDTO)
            else -> address.id?.let { this.repository.update(it, addressDTO) } ?: throw NotFoundException(address.cep)
        }
    }

    override fun getAll() = this.repository.getAll()
}
