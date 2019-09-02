package io.github.emikaelsilveira.controllers

import io.github.emikaelsilveira.application.web.controllers.AddressController
import io.github.emikaelsilveira.domain.services.AddressService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AddressControllerTest {

    private val addressService = mockk<AddressService>()

    @Test
    fun `should get a list of address`() {
        every { addressService.getAll() } returns listOf()
        val allAddress = AddressController(addressService).getAll()
        assertThat(allAddress).isNotNull

        verify { addressService.getAll() }
    }
}
