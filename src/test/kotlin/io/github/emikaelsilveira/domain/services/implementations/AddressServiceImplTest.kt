package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.environment.createAddress
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

private const val ID = 1L
private const val CEP = "88708-001"

object AddressServiceImplTest : Spek({

    val provider = mockk<AddressProvider>()
    val repository = mockk<AddressRepository>()
    val service = AddressServiceImpl(provider, repository)

    describe("#AddressService") {

        afterEachTest {
            confirmVerified(provider, repository)
            clearAllMocks()
        }

        describe("AddressServiceImpl.getByCep(cep: String): AddressDTO") {
            it("should return a address by cep") {
                every { provider.getAddress(CEP).toDomain() } returns createAddress()

                val result = service.getByCep(CEP)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(createAddress())
                verify { provider.getAddress(CEP).toDomain() }
            }
        }

        describe("AddressServiceImpl.getAll(): List<AddressDTO>") {
            it("should return all address") {
                val listAddress = listOf(createAddress())
                every { repository.getAll() } returns listAddress

                val result = service.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).size().isEqualTo(1)
                assertThat(result).isEqualTo(listAddress)
                verify { repository.getAll() }
            }
        }

        describe("AddressServiceImpl.createOrUpdate(address: AddressDTO): AddressDTO") {
            val address = createAddress()
            it("should create a new address on database") {
                every { repository.getByCep(address.cep) } returns null
                every { repository.create(address) } returns createAddress()

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
                every { repository.update(ID, address) } returns createAddress()

                val result = service.createOrUpdate(address)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    repository.getByCep(address.cep)
                    repository.update(ID, address)
                }

                verify(exactly = 0) { repository.create(any()) }
            }
        }
    }
})
