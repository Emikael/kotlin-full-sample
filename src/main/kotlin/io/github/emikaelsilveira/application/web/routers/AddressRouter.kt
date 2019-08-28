package io.github.emikaelsilveira.application.web.routers

import io.github.emikaelsilveira.application.web.controllers.AddressController
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path

class AddressRouter(private val controller: AddressController) {

    companion object {
        private const val PATH_ADDRESS = "address"
        private const val PARAM_CEP = ":cep"
    }

    fun register() {
        path(PATH_ADDRESS) {
            get(this.controller.getAll())
            path(PARAM_CEP) {
                get(this.controller.getByCep())
            }
        }
    }
}
