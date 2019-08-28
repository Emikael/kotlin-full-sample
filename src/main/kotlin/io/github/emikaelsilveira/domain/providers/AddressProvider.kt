package io.github.emikaelsilveira.domain.providers

import io.github.emikaelsilveira.domain.entities.AddressConvertable

interface AddressProvider {

    fun getAddress(cep: String): AddressConvertable
}
