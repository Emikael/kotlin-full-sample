package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.services.UserService
import io.github.emikaelsilveira.resource.extensions.paramAsLong
import io.javalin.http.Handler

class UserController(private val service: UserService) {

    companion object {
        private const val USER_ID = "id"
    }

    fun getAll() = Handler { it.json(this.service.getAll()) }

    fun getOne() = Handler { it.json(this.service.getOne(it.paramAsLong(USER_ID))) }

    fun create() = Handler { it.json(this.service.create(it.bodyAsClass(UserDTO::class.java))) }

    fun update() = Handler {
        val id = it.paramAsLong(USER_ID)
        val user = it.bodyAsClass(UserDTO::class.java)
        this.service.update(id, user)
    }

    fun delete() = Handler { it.json(this.service.delete(it.paramAsLong(USER_ID))) }
}
