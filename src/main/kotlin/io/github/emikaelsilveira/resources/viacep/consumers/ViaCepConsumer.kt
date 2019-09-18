package io.github.emikaelsilveira.resources.viacep.consumers

import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import io.github.emikaelsilveira.application.config.ignoreUnknownProperties
import io.github.emikaelsilveira.application.config.objectMapper
import io.github.emikaelsilveira.domain.entities.AddressConvertable
import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.resources.viacep.entities.ViaCepAddress
import io.github.emikaelsilveira.resources.viacep.exceptions.APIException

class ViaCepConsumer : AddressProvider {

    companion object {
        private const val VIA_CEP_HOST = "https://viacep.com.br/ws/%s/json"
    }

    private val customObjectMapper = objectMapper.copy().ignoreUnknownProperties()

    override fun getAddress(cep: String): AddressConvertable {
        val (_, response, result) = Fuel.get(VIA_CEP_HOST.format(cep)).responseString()
        return result.fold(
            { content -> successRequest(content) },
            { throw APIException(response.data.let { customObjectMapper.readValue<String>(it) }) }
        )
    }

    private fun successRequest(content: String) = try {
        this.customObjectMapper.readValue<ViaCepAddress>(content)
    } catch (ex: APIException) {
        throw ex
    }
}
