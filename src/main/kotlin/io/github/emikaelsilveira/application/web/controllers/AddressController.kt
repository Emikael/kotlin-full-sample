package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.services.AddressService
import io.javalin.http.Context

class AddressController(private val service: AddressService) {

    companion object {
        private const val CEP_PARAM = "cep"
    }

    fun getByCep(context: Context) =
        service.getByCep(context.pathParam(CEP_PARAM)).let { service.createOrUpdate(it) }

    fun getAll() = service.getAll()
}
