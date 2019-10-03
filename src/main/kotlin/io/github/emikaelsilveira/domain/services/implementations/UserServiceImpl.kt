package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.entities.User
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.domain.services.UserService

class UserServiceImpl(
    private val repository: UserRepository,
    private val addressService: AddressService
) : UserService {

    override fun getAll() = repository.getAll()

    override fun getOne(id: Long) = repository.getOne(id)

    override fun create(userParameter: User): User {
        val user = getUserWithAddress(userParameter)
        return repository.create(user)
    }

    override fun update(id: Long, userParameter: User): User {
        val user = getUserWithAddress(userParameter)
        return repository.update(id, user)
    }

    override fun delete(id: Long) = repository.delete(id)

    private fun getUserWithAddress(user: User): User {
        val address = user.address?.cep
            ?.let { addressService.getByCep(it) }
            ?.let { addressService.createOrUpdate(it) }
        return user.copy(address = address)
    }
}
