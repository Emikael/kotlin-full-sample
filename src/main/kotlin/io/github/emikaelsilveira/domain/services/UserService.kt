package io.github.emikaelsilveira.domain.services

import io.github.emikaelsilveira.domain.entities.UserDTO

interface UserService {

    fun getAll(): List<UserDTO>

    fun getOne(id: Long): UserDTO

    fun create(userDTO: UserDTO): UserDTO

    fun update(id: Long, userDTO: UserDTO): UserDTO

    fun delete(id: Long)
}
