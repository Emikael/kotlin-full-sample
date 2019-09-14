package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.builders.AddressDTOBuilder
import io.github.emikaelsilveira.domain.services.AddressService
import io.javalin.http.Context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object AddressControllerTest : Spek({

    val cep = "88708-001"
    val cepParam = "cep"
    val service = mockk<AddressService>()
    val context = mockk<Context>()
    val controller = AddressController(service)

    describe("#AddressController") {

        afterEachTest { confirmVerified(service, context) }

        describe("GET /Address") {
            it("should get a list of address") {
                val mockListAddress = listOf(AddressDTOBuilder.build())
                every { service.getAll() } returns mockListAddress

                val result = controller.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).isEqualTo(mockListAddress)
                verify { service.getAll() }
            }
        }

        describe("GET /Address/:cep") {
            it("should get a address by cep and create or update on database") {
                val address = AddressDTOBuilder.build()

                every { context.pathParam(cepParam) } returns cep
                every { service.getByCep(cep) } returns address
                every { service.createOrUpdate(address) } returns address

                val result = controller.getByCep(context)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    context.pathParam(cepParam)
                    service.getByCep(cep)
                    service.createOrUpdate(address)
                }
            }
        }
    }
})
