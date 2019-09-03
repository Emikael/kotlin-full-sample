package io.github.emikaelsilveira.domain.services.implementations

import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.environment.createAddress
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class AddressServiceImplTest {

    private val provider = mockk<AddressProvider>()
    private val repository = mockk<AddressRepository>()

    private val service = AddressServiceImpl(provider, repository)

    companion object {
        private const val ID = 1L
        private const val CEP = "88708-001"
    }

    @AfterEach
    fun down() = confirmVerified(repository, provider)

    @Test
    fun `should return a address by cep`() {
        every { provider.getAddress(CEP).toDomain() } returns createAddress()

        val result = service.getByCep(CEP)

        assertThat(result).isEqualTo(createAddress())
        verify { provider.getAddress(CEP).toDomain() }
    }

    @Test
    fun `should return all address`() {
        val listAddress = listOf(createAddress())
        every { repository.getAll() } returns listAddress

        val result = service.getAll()

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
        assertThat(result).size().isEqualTo(1)
        assertThat(result).isEqualTo(listAddress)
        verify { repository.getAll() }
    }

    @Test
    fun `should create a new address on database`() {
        val address = createAddress()
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

    @Test
    fun `should update a address on database`() {
        val address = createAddress()
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
