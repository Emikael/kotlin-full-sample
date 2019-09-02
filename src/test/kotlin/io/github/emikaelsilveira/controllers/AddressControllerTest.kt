package io.github.emikaelsilveira.controllers

import io.github.emikaelsilveira.application.web.controllers.AddressController
import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.environment.createAddress
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyAll
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AddressControllerTest {

    private val service = mockk<AddressService>()
    private val context = mockk<Context>()

    private val controller = AddressController(service)

    companion object {
        private const val CEP_PARAM = "cep"
        private const val CEP = "88708-001"
    }

    @Test
    fun `should get a list of address`() {
        val mockListAddress = listOf(createAddress())
        every { service.getAll() } returns mockListAddress

        val allAddress = controller.getAll()

        assertThat(allAddress).isNotNull
        assertThat(allAddress).isNotEmpty
        assertThat(allAddress).isEqualTo(mockListAddress)
        verify { service.getAll() }
    }

    @Test
    fun `should get a address by cep`() {
        every { context.pathParam(CEP_PARAM) } returns CEP
        every { service.getByCep(CEP) } returns createAddress()

        val address = controller.getByCep(context)

        assertThat(address).isNotNull
        assertThat(address).isEqualTo(createAddress())
        verifyAll {
            context.pathParam(CEP_PARAM)
            service.getByCep(CEP)
        }
    }
}
