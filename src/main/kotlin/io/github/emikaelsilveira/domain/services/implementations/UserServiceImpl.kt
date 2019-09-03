package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.domain.services.UserService

class UserServiceImpl(
    private val repository: UserRepository,
    private val addressService: AddressService
) : UserService {

    override fun getAll() = this.repository.getAll()

    override fun getOne(id: Long) = this.repository.getOne(id)

    override fun create(userDTO: UserDTO): UserDTO {
        val user = getUserWithAddress(userDTO)
        return this.repository.create(user)
    }

    override fun update(id: Long, userDTO: UserDTO): UserDTO {
        val user = getUserWithAddress(userDTO)
        return this.repository.update(id, user)
    }

    override fun delete(id: Long) = this.repository.delete(id)

    private fun getUserWithAddress(userDTO: UserDTO): UserDTO {
        val address = userDTO.addressDTO?.cep?.let { this.addressService.getByCep(it) }
        return userDTO.copy(addressDTO = address)
    }
}
