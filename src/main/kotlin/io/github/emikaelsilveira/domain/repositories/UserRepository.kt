package io.github.emikaelsilveira.domain.repositories

import io.github.emikaelsilveira.domain.entities.UserDTO

interface UserRepository {

    fun getAll(): UserDTO

    fun getOne(id: Long): UserDTO

    fun create()
}
