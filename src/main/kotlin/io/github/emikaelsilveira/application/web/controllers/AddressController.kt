package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.services.AddressService
import io.javalin.http.Context

class AddressController(private val service: AddressService) {

    companion object {
        private const val CEP_PARAM = "cep"
    }

    fun getByCep(context: Context) = this.service.getByCep(context.pathParam(CEP_PARAM))

    fun getAll() = this.service.getAll()
}
