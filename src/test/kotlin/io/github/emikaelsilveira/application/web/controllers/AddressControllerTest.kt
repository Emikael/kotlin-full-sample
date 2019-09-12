package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.environment.createAddress
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

    val service = mockk<AddressService>()
    val context = mockk<Context>()
    val controller = AddressController(service)

    describe("#AddressController") {

        afterEachTest { confirmVerified(service, context) }

        describe("GET /Address") {
            it("should get a list of address") {
                val mockListAddress = listOf(createAddress())
                every { service.getAll() } returns mockListAddress

                val allAddress = controller.getAll()

                assertThat(allAddress).isNotNull
                assertThat(allAddress).isNotEmpty
                assertThat(allAddress).isEqualTo(mockListAddress)
                verify { service.getAll() }
            }
        }

        describe("GET /Address/:cep") {
            it("should get a address by cep and create or update on database") {
                val cep = "88708-001"
                val cepParam = "cep"
                val objectAddress = createAddress()

                every { context.pathParam(cepParam) } returns cep
                every { service.getByCep(cep) } returns objectAddress
                every { service.createOrUpdate(objectAddress) } returns objectAddress

                val address = controller.getByCep(context)

                assertThat(address).isNotNull
                assertThat(address).isEqualTo(createAddress())
                verify {
                    context.pathParam(cepParam)
                    service.getByCep(cep)
                    service.createOrUpdate(objectAddress)
                }
            }
        }
    }
})
