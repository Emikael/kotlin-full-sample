package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.domain.services.UserService

class UserServiceImpl(private val repository: UserRepository) : UserService {

    override fun getAll() = this.repository.getAll()

    override fun getOne(id: Long) = this.repository.getOne(id)

    override fun create(userDTO: UserDTO) = this.repository.create(userDTO)

    override fun update(id: Long, userDTO: UserDTO) = this.repository.update(id, userDTO)

    override fun delete(id: Long) = this.repository.delete(id)
}
