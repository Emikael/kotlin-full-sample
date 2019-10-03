package io.github.emikaelsilveira.domain.services

import io.github.emikaelsilveira.domain.entities.User

interface UserService {

    fun getAll(): List<User>

    fun getOne(id: Long): User

    fun create(user: User): User

    fun update(id: Long, user: User): User

    fun delete(id: Long)
}
