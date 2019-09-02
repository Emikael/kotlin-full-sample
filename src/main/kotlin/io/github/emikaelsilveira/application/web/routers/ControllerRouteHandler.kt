package io.github.emikaelsilveira.application.web.routers

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus.OK_200
import kotlin.reflect.KFunction

class ControllerRouteHandler {

    fun register(context: Context, controllerHandler: KFunction<Any>) {
        when (controllerHandler.parameters.isEmpty()) {
            true -> context.json(controllerHandler.call())
            false -> context.json(controllerHandler.call(context))
        }
        context.status(OK_200)
    }
}
