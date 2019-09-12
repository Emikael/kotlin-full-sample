package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.environment.createAddress
import io.github.emikaelsilveira.environment.createUser
import io.github.emikaelsilveira.environment.createUser2
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

private const val ID = 1L
private const val CEP = "88708-001"

class UserServiceImplTest : Spek({

    val repository = mockk<UserRepository>()
    val addressService = mockk<AddressService>()
    val service = UserServiceImpl(repository, addressService)

    describe("#UserService") {

        afterEachTest { confirmVerified(repository, addressService) }

        describe("UserServiceImpl.getAll(): List<UserDTO>") {
            it("should returns all users") {
                val listUsers = listOf(createUser())
                every { repository.getAll() } returns listUsers

                val result = service.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).size().isEqualTo(1)
                assertThat(result).isEqualTo(listUsers)

                verify { repository.getAll() }
            }
        }

        describe("UserServiceImpl.getOne(id: Long): UserDTO") {
            it("should return a user by id") {
                every { repository.getOne(ID) } returns createUser()

                val result = service.getOne(ID)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser())

                verify { repository.getOne(ID) }
            }
        }

        describe("UserServiceImpl.create(user: UserDTO): UserDTO") {
            it("should create a user") {
                val user = createUser()
                every { repository.create(user) } returns createUser2()
                every { addressService.getByCep(CEP) } returns createAddress()

                val result = service.create(user)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser2())

                verify {
                    repository.create(user)
                    addressService.getByCep(CEP)
                }
            }
        }

        describe("UserServiceImpl.update(id: Long, user: UserDTO): UserDTO") {
            it("should update a user") {
                val user = createUser()
                every { repository.update(ID, user) } returns createUser2()
                every { addressService.getByCep(CEP) } returns createAddress()

                val result = service.update(ID, user)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createUser2())

                verify {
                    repository.update(ID, user)
                    addressService.getByCep(CEP)
                }
            }
        }

        describe("UserServiceImpl.delete(id: Long)") {
            it("should delete a user") {
                every { repository.delete(ID) } just runs

                service.delete(ID)

                verify { repository.delete(ID) }
            }
        }
    }
})
