package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.domain.services.AddressService

class AddressServiceImpl(private val repository: AddressRepository) : AddressService {

    override fun getByCep(cep: String) = this.repository.getByCep(cep)

    override fun create(addressDTO: AddressDTO) = this.repository.create(addressDTO)

    override fun update(id: Long, addressDTO: AddressDTO) = this.repository.update(id, addressDTO)
}
