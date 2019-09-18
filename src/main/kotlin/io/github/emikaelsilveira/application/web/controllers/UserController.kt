package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.services.UserService
import io.github.emikaelsilveira.resources.extensions.paramAsLong
import io.javalin.http.Context

class UserController(private val service: UserService) {

    companion object {
        private const val USER_ID = "id"
    }

    fun getAll() = this.service.getAll()

    fun getOne(context: Context) = this.service.getOne(context.paramAsLong(USER_ID))

    fun create(context: Context) = this.service.create(context.bodyAsClass(UserDTO::class.java))

    fun update(context: Context): UserDTO {
        val id = context.paramAsLong(USER_ID)
        val user = context.bodyAsClass(UserDTO::class.java)
        return this.service.update(id, user)
    }

    fun delete(context: Context) = this.service.delete(context.paramAsLong(USER_ID))
}
