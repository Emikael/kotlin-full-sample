package io.github.emikaelsilveira.application.web.routers

import io.github.emikaelsilveira.application.web.controllers.AddressController
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class AddressRouter(
    private val controller: AddressController,
    private val handler: ControllerRouteHandler
) : Router {

    companion object {
        private const val PATH_ADDRESS = "address"
        private const val PARAM_CEP = ":cep"
    }

    override fun register() {
        path(PATH_ADDRESS) {
            get { this.handler.register(it, this.controller::getAll) }
            path(PARAM_CEP) {
                post { this.handler.register(it, this.controller::getByCep) }
            }
        }
    }
}
