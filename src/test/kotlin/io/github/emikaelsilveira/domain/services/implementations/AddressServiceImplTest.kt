package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.builders.AddressDTOBuilder
import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object AddressServiceImplTest : Spek({

    val uuId = 1L
    val cep = "88708-001"
    val provider = mockk<AddressProvider>()
    val repository = mockk<AddressRepository>()
    val service = AddressServiceImpl(provider, repository)
    val address = AddressDTOBuilder.build()

    describe("#AddressService") {

        afterEachTest {
            confirmVerified(provider, repository)
            clearAllMocks()
        }

        describe("AddressServiceImpl.getByCep(cep: String): AddressDTO") {
            it("should return a address by cep") {
                every { provider.getAddress(cep).toDomain() } returns address

                val result = service.getByCep(cep)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify { provider.getAddress(cep).toDomain() }
            }
        }

        describe("AddressServiceImpl.getAll(): List<AddressDTO>") {
            it("should return all address") {
                val addressList = listOf(address)
                every { repository.getAll() } returns addressList

                val result = service.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).size().isEqualTo(1)
                assertThat(result).isEqualTo(addressList)
                verify { repository.getAll() }
            }
        }

        describe("AddressServiceImpl.createOrUpdate(address: AddressDTO): AddressDTO") {
            it("should create a new address on database") {
                every { repository.getByCep(address.cep) } returns null
                every { repository.create(address) } returns address

                val result = service.createOrUpdate(address)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    repository.getByCep(address.cep)
                    repository.create(address)
                }

                verify(exactly = 0) { repository.update(any(), any()) }
            }

            it("should update a address on database") {
                every { repository.getByCep(address.cep) } returns address
                every { repository.update(uuId, address) } returns address

                val result = service.createOrUpdate(address)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    repository.getByCep(address.cep)
                    repository.update(uuId, address)
                }

                verify(exactly = 0) { repository.create(any()) }
            }
        }
    }
})
