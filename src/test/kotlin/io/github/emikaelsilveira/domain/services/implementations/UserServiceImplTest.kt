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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class UserServiceImplTest {

    private val repository = mockk<UserRepository>()
    private val addressService = mockk<AddressService>()

    private val service = UserServiceImpl(repository, addressService)

    companion object {
        private const val ID = 1L
        private const val CEP = "88708-001"
    }

    @AfterEach
    fun down() = confirmVerified(repository, addressService)

    @Test
    fun `should returns all users`() {
        val listUsers = listOf(createUser())
        every { repository.getAll() } returns listUsers

        val result = service.getAll()

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
        assertThat(result).size().isEqualTo(1)
        assertThat(result).isEqualTo(listUsers)

        verify { repository.getAll() }
    }

    @Test
    fun `should return a user by id`() {
        every { repository.getOne(ID) } returns createUser()

        val result = service.getOne(ID)

        assertThat(result).isNotNull
        assertThat(result).isEqualTo(createUser())

        verify { repository.getOne(ID) }
    }

    @Test
    fun `should create a user`() {
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

    @Test
    fun `should update a user`() {
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

    @Test
    fun `should delete a user`() {
        every { repository.delete(ID) } just runs

        service.delete(ID)

        verify { repository.delete(ID) }
    }
}
