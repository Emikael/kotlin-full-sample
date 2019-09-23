package io.github.emikaelsilveira.controllers

import io.github.emikaelsilveira.application.web.controllers.AddressController
import io.github.emikaelsilveira.utils.builders.AddressDTOBuilder
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
    val serviceMock = mockk<AddressService>()
    val contextMock = mockk<Context>()
    val controller = AddressController(serviceMock)

    describe("#AddressController") {

        afterEachTest { confirmVerified(serviceMock, contextMock) }

        describe("GET /Address") {
            it("should get a list of address") {
                val mockListAddress = listOf(AddressDTOBuilder.build())
                every { serviceMock.getAll() } returns mockListAddress

                val result = controller.getAll()

                assertThat(result).isNotNull
                assertThat(result).isNotEmpty
                assertThat(result).isEqualTo(mockListAddress)
                verify { serviceMock.getAll() }
            }
        }

        describe("GET /Address/:cep") {
            it("should get a address by cep and create or update on database") {
                val address = AddressDTOBuilder.build()

                every { contextMock.pathParam(cepParam) } returns cep
                every { serviceMock.getByCep(cep) } returns address
                every { serviceMock.createOrUpdate(address) } returns address

                val result = controller.getByCep(contextMock)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(address)
                verify {
                    contextMock.pathParam(cepParam)
                    serviceMock.getByCep(cep)
                    serviceMock.createOrUpdate(address)
                }
            }
        }
    }
})
