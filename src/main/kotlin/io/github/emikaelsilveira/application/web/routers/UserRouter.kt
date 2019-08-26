package io.github.emikaelsilveira.application.web.routers

import io.github.emikaelsilveira.application.web.controllers.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.ApiBuilder.put

class UserRouter(private val controller: UserController) {

    companion object {
        private const val PATH_USERS = "users"
        private const val PATH_ID_PARAM = ":id"
    }

    fun register(app: Javalin) {
        app.routes {
            path(PATH_USERS) {
                get(this.controller.getAll())
                post(this.controller.create())

                path(PATH_ID_PARAM) {
                    get(this.controller.getOne())
                    put(this.controller.update())
                    delete(this.controller.delete())
                }
            }
        }
    }
}
