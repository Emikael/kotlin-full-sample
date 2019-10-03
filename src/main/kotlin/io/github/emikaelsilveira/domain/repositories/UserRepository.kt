package io.github.emikaelsilveira.domain.repositories

import io.github.emikaelsilveira.domain.entities.User

interface UserRepository {

    fun getAll(): List<User>

    fun getOne(id: Long): User

    fun create(user: User): User

    fun update(id: Long, user: User): User

    fun delete(id: Long)
}
