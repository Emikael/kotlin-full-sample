package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.environment.createAddress
import io.javalin.http.Context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class AddressControllerTest {

    private val service = mockk<AddressService>()
    private val context = mockk<Context>()

    private val controller = AddressController(service)

    companion object {
        private const val CEP_PARAM = "cep"
        private const val CEP = "88708-001"
    }

    @AfterEach
    fun down() = confirmVerified(service, context)

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
    fun `should get a address by cep and create or update on database`() {
        val objectAddress = createAddress()
        every { context.pathParam(CEP_PARAM) } returns CEP
        every { service.getByCep(CEP) } returns objectAddress
        every { service.createOrUpdate(objectAddress) } returns objectAddress

        val address = controller.getByCep(context)

        assertThat(address).isNotNull
        assertThat(address).isEqualTo(createAddress())
        verify {
            context.pathParam(CEP_PARAM)
            service.getByCep(CEP)
            service.createOrUpdate(objectAddress)
        }
    }
}
