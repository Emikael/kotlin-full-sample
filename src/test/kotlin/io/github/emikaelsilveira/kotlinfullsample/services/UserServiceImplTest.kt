package io.github.emikaelsilveira.kotlinfullsample.services

import io.github.emikaelsilveira.utils.builders.AddressDTOBuilder
import io.github.emikaelsilveira.utils.builders.UserDTOBuilder
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.domain.services.implementations.UserServiceImpl
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class UserServiceImplTest : Spek({

    val uuId = 1L
    val cep = "88708-000"
    val repositoryMock = mockk<UserRepository>()
    val addressServiceMock = mockk<AddressService>()
    val service =
        UserServiceImpl(repositoryMock, addressServiceMock)
    val user = UserDTOBuilder.build()
    val otherUser = UserDTOBuilder.build { id = 2 }
    val address = AddressDTOBuilder.build()

    describe("#UserService") {

        afterEachTest { confirmVerified(repositoryMock, addressServiceMock) }

        describe("UserServiceImpl.getAll(): List<UserDTO>") {
            it("should returns all users") {
                val userList = listOf(user)
                every { repositoryMock.getAll() } returns userList

                val result = service.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).size().isOne
                assertThat(result).isEqualTo(userList)

                verify { repositoryMock.getAll() }
            }
        }

        describe("UserServiceImpl.getOne(id: Long): UserDTO") {
            it("should return a user by id") {
                every { repositoryMock.getOne(uuId) } returns user

                val result = service.getOne(uuId)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(user)
                verify { repositoryMock.getOne(uuId) }
            }
        }

        describe("UserServiceImpl.create(user: UserDTO): UserDTO") {
            it("should create a user") {
                every { repositoryMock.create(user) } returns otherUser
                every { addressServiceMock.getByCep(cep) } returns address

                val result = service.create(user)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(otherUser)
                verify {
                    repositoryMock.create(user)
                    addressServiceMock.getByCep(cep)
                }
            }
        }

        describe("UserServiceImpl.update(id: Long, user: UserDTO): UserDTO") {
            it("should update a user") {
                every { repositoryMock.update(uuId, user) } returns otherUser
                every { addressServiceMock.getByCep(cep) } returns address

                val result = service.update(uuId, user)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(otherUser)

                verify {
                    repositoryMock.update(uuId, user)
                    addressServiceMock.getByCep(cep)
                }
            }
        }

        describe("UserServiceImpl.delete(id: Long)") {
            it("should delete a user") {
                every { repositoryMock.delete(uuId) } just runs

                service.delete(uuId)

                verify { repositoryMock.delete(uuId) }
            }
        }
    }
})
