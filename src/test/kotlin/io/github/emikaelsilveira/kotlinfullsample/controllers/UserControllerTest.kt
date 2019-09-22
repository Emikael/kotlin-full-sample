package io.github.emikaelsilveira.kotlinfullsample.controllers

import io.github.emikaelsilveira.application.web.controllers.UserController
import io.github.emikaelsilveira.utils.builders.UserDTOBuilder
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
    val serviceMock = mockk<UserService>()
    val contextMock = mockk<Context>()
    val controller = UserController(serviceMock)
    val user = UserDTOBuilder.build()
    val otherUser = UserDTOBuilder.build { id = 2 }

    describe("#UserController") {

        afterEachTest { confirmVerified(serviceMock, contextMock) }

        describe("GET /users") {
            it("should get a list of users") {
                val userList = listOf(UserDTOBuilder.build())
                every { serviceMock.getAll() } returns userList

                val result = controller.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).isEqualTo(userList)
                verify { serviceMock.getAll() }
            }
        }

        describe("GET /users/:id") {
            it("should get a user by id") {
                every { contextMock.pathParam(userId) } returns uuId
                every { serviceMock.getOne(uuId.toLong()) } returns user

                val result = controller.getOne(contextMock)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(user)
                verify {
                    contextMock.pathParam(userId)
                    serviceMock.getOne(uuId.toLong())
                }
            }
        }

        describe("POST /users") {
            it("should create a new user") {
                every { contextMock.bodyAsClass(UserDTO::class.java) } returns user
                every { serviceMock.create(user) } returns otherUser

                val result = controller.create(contextMock)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(otherUser)
                verify {
                    contextMock.bodyAsClass(UserDTO::class.java)
                    serviceMock.create(user)
                }
            }
        }

        describe("PUT /users/:id") {
            it("should update a user") {
                every { contextMock.pathParam(userId) } returns uuId
                every { contextMock.bodyAsClass(UserDTO::class.java) } returns user
                every { serviceMock.update(uuId.toLong(), user) } returns otherUser

                val result = controller.update(contextMock)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(otherUser)
                verify {
                    contextMock.pathParam(userId)
                    contextMock.bodyAsClass(UserDTO::class.java)
                    serviceMock.update(uuId.toLong(), user)
                }
            }
        }

        describe("DELETE /users/:id") {
            it("should delete a user") {
                every { contextMock.pathParam(userId) } returns uuId
                every { serviceMock.delete(uuId.toLong()) } returns Unit

                controller.delete(contextMock)

                verify {
                    contextMock.pathParam(userId)
                    serviceMock.delete(uuId.toLong())
                }
            }
        }
    }
})
