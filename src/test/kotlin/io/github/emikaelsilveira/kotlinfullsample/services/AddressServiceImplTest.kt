package io.github.emikaelsilveira.kotlinfullsample.services

import io.github.emikaelsilveira.utils.builders.AddressDTOBuilder
import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.domain.services.implementations.AddressServiceImpl
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
    val providerMock = mockk<AddressProvider>()
    val repositoryMock = mockk<AddressRepository>()
    val service =
        AddressServiceImpl(providerMock, repositoryMock)
    val address = AddressDTOBuilder.build()

    describe("#AddressService") {

        afterEachTest {
            confirmVerified(providerMock, repositoryMock)
            clearAllMocks()
        }

        describe("AddressServiceImpl.getByCep(cep: String): AddressDTO") {
            it("should return a address by cep") {
                every { providerMock.getAddress(cep).toDomain() } returns address

                val result = service.getByCep(cep)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify { providerMock.getAddress(cep).toDomain() }
            }
        }

        describe("AddressServiceImpl.getAll(): List<AddressDTO>") {
            it("should return all address") {
                val addressList = listOf(address)
                every { repositoryMock.getAll() } returns addressList

                val result = service.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).size().isOne
                assertThat(result).isEqualTo(addressList)
                verify { repositoryMock.getAll() }
            }
        }

        describe("AddressServiceImpl.createOrUpdate(address: AddressDTO): AddressDTO") {
            it("should create a new address on database") {
                every { repositoryMock.getByCep(address.cep) } returns null
                every { repositoryMock.create(address) } returns address

                val result = service.createOrUpdate(address)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    repositoryMock.getByCep(address.cep)
                    repositoryMock.create(address)
                }

                verify(exactly = 0) { repositoryMock.update(any(), any()) }
            }

            it("should update a address on database") {
                every { repositoryMock.getByCep(address.cep) } returns address
                every { repositoryMock.update(uuId, address) } returns address

                val result = service.createOrUpdate(address)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    repositoryMock.getByCep(address.cep)
                    repositoryMock.update(uuId, address)
                }

                verify(exactly = 0) { repositoryMock.create(any()) }
            }
        }
    }
})
