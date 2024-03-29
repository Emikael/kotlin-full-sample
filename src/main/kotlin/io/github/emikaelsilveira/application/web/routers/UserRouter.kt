package io.github.emikaelsilveira.application.web.routers

import io.github.emikaelsilveira.application.web.controllers.UserController
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.ApiBuilder.put

class UserRouter(
    private val controller: UserController,
    private val handler: ControllerRouteHandler
) : Router {

    companion object {
        private const val PATH_USERS = "users"
        private const val PATH_ID_PARAM = ":id"
    }

    override fun register() {
        path(PATH_USERS) {
            get { this.handler.register(it, this.controller::getAll) }
            post { this.handler.register(it, this.controller::create) }

            path(PATH_ID_PARAM) {
                get { this.handler.register(it, this.controller::getOne) }
                put { this.handler.register(it, this.controller::update) }
                delete { this.handler.register(it, this.controller::delete) }
            }
        }
    }
}
