package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.builders.UserDTOBuilder
import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.services.UserService
import io.javalin.http.Context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object UserControllerTest : Spek({

    val userId = "id"
    val uuId = "1"
    val service = mockk<UserService>()
    val context = mockk<Context>()
    val controller = UserController(service)
    val user = UserDTOBuilder.build()
    val createUser = UserDTOBuilder.build { id = 2 }

    describe("#UserController") {

        afterEachTest { confirmVerified(service, context) }

        describe("GET /users") {
            it("should get a list of users") {
                val userList = listOf(UserDTOBuilder.build())
                every { service.getAll() } returns userList

                val result = controller.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).isEqualTo(userList)
                verify { service.getAll() }
            }
        }

        describe("GET /users/:id") {
            it("should get a user by id") {
                every { context.pathParam(userId) } returns uuId
                every { service.getOne(uuId.toLong()) } returns user

                val result = controller.getOne(context)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(user)
                verify {
                    context.pathParam(userId)
                    service.getOne(uuId.toLong())
                }
            }
        }

        describe("POST /users") {
            it("should create a new user") {
                every { context.bodyAsClass(UserDTO::class.java) } returns user
                every { service.create(user) } returns createUser

                val result = controller.create(context)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser)
                verify {
                    context.bodyAsClass(UserDTO::class.java)
                    service.create(user)
                }
            }
        }

        describe("PUT /users/:id") {
            it("should update a user") {
                every { context.pathParam(userId) } returns uuId
                every { context.bodyAsClass(UserDTO::class.java) } returns user
                every { service.update(uuId.toLong(), user) } returns createUser

                val result = controller.update(context)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser)
                verify {
                    context.pathParam(userId)
                    context.bodyAsClass(UserDTO::class.java)
                    service.update(uuId.toLong(), user)
                }
            }
        }

        describe("DELETE /users/:id") {
            it("should delete a user") {
                every { context.pathParam(userId) } returns uuId
                every { service.delete(uuId.toLong()) } returns Unit

                controller.delete(context)

                verify {
                    context.pathParam(userId)
                    service.delete(uuId.toLong())
                }
            }
        }
    }
})
