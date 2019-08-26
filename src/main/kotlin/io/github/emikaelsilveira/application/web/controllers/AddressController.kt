package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.resource.extensions.paramAsLong
import io.javalin.http.Handler

class AddressController(private val service: AddressService) {

    companion object {
        private const val CEP_PARAM = "cep"
        private const val ADDRESS_ID = "id"
    }

    fun getByCep() = Handler { it.json(this.service.getByCep(it.pathParam(CEP_PARAM))) }

    fun create() = Handler { it.json(this.service.create(it.bodyAsClass(AddressDTO::class.java))) }

    fun update() = Handler {
        val id = it.paramAsLong(ADDRESS_ID)
        val address = it.bodyAsClass(AddressDTO::class.java)
        this.service.update(id, address)
    }
}
