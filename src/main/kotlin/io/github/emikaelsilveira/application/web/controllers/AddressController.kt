package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.services.AddressService
import io.javalin.http.Handler

class AddressController(private val service: AddressService) {

    companion object {
        private const val CEP_PARAM = "cep"
    }

    fun getByCep() = Handler { it.json(this.service.getByCep(it.pathParam(CEP_PARAM))) }

    fun getAll() = Handler { it.json(this.service.getAll()) }
}
