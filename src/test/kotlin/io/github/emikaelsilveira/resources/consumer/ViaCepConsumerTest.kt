package io.github.emikaelsilveira.resources.consumer

import com.github.kittinunf.fuel.core.Client
import com.github.kittinunf.fuel.core.FuelManager
import io.github.emikaelsilveira.utils.extensions.payload
import io.github.emikaelsilveira.resources.viacep.consumers.ViaCepConsumer
import io.github.emikaelsilveira.resources.viacep.exceptions.APIException
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400
import org.eclipse.jetty.http.HttpStatus.OK_200
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object ViaCepConsumerTest : Spek({

    val consumer = ViaCepConsumer()
    val clientMock = mockk<Client>()
    val cep = "88708-001"

    describe("#ViaCepConsumer") {

        beforeEachTest {
            FuelManager.instance.client = clientMock
        }

        describe("ViaCepConsumer.getAddress(cep: String): AddressConvertable") {
            it("should return an address from ViaCep.com.br") {
                every { clientMock.executeRequest(any()).statusCode } returns OK_200
                every { clientMock.executeRequest(any()).responseMessage } returns "OK"
                every { clientMock.executeRequest(any()).data } returns "viacep-payload.json".payload().toByteArray()

                val result = consumer.getAddress(cep)

                assertThat(result).isNotNull
                assertThat(result.toDomain().cep).isEqualTo(cep)
            }

            it("should return an exception when fetching an address") {
                every { clientMock.executeRequest(any()).statusCode } returns BAD_REQUEST_400
                every { clientMock.executeRequest(any()).responseMessage } returns "BAD_REQUEST"
                every {
                    clientMock.executeRequest(any()).data
                } returns "viacep-bad-request-payload.html".payload().toByteArray()

                assertThatThrownBy {
                    consumer.getAddress(cep)
                }.isInstanceOf(APIException::class.java)
                    .hasMessage("<h3>ViaCEP Bad Request (400)</h3>")
            }
        }
    }
})
