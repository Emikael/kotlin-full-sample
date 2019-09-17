package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.builders.AddressDTOBuilder
import io.github.emikaelsilveira.builders.UserDTOBuilder
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.domain.services.AddressService
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
    val cep = "88708-001"
    val repository = mockk<UserRepository>()
    val addressService = mockk<AddressService>()
    val service = UserServiceImpl(repository, addressService)
    val user = UserDTOBuilder.build()
    val createUser = UserDTOBuilder.build { id = 2 }
    val address = AddressDTOBuilder.build()

    describe("#UserService") {

        afterEachTest { confirmVerified(repository, addressService) }

        describe("UserServiceImpl.getAll(): List<UserDTO>") {
            it("should returns all users") {
                val userList = listOf(user)
                every { repository.getAll() } returns userList

                val result = service.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).size().isOne
                assertThat(result).isEqualTo(userList)

                verify { repository.getAll() }
            }
        }

        describe("UserServiceImpl.getOne(id: Long): UserDTO") {
            it("should return a user by id") {
                every { repository.getOne(uuId) } returns user

                val result = service.getOne(uuId)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(user)
                verify { repository.getOne(uuId) }
            }
        }

        describe("UserServiceImpl.create(user: UserDTO): UserDTO") {
            it("should create a user") {
                every { repository.create(user) } returns createUser
                every { addressService.getByCep(cep) } returns address

                val result = service.create(user)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser)
                verify {
                    repository.create(user)
                    addressService.getByCep(cep)
                }
            }
        }

        describe("UserServiceImpl.update(id: Long, user: UserDTO): UserDTO") {
            it("should update a user") {
                every { repository.update(uuId, user) } returns createUser
                every { addressService.getByCep(cep) } returns address

                val result = service.update(uuId, user)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser)

                verify {
                    repository.update(uuId, user)
                    addressService.getByCep(cep)
                }
            }
        }

        describe("UserServiceImpl.delete(id: Long)") {
            it("should delete a user") {
                every { repository.delete(uuId) } just runs

                service.delete(uuId)

                verify { repository.delete(uuId) }
            }
        }
    }
})
