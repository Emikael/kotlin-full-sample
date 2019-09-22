package io.github.emikaelsilveira.application.config

import io.github.emikaelsilveira.application.config.modules.configModule
import io.github.emikaelsilveira.application.config.modules.controllerModule
import io.github.emikaelsilveira.application.config.modules.dataBaseModule
import io.github.emikaelsilveira.application.config.modules.providerModule
import io.github.emikaelsilveira.application.config.modules.repositoryModule
import io.github.emikaelsilveira.application.config.modules.routerModule
import io.github.emikaelsilveira.application.config.modules.serviceModule
import io.github.emikaelsilveira.application.web.routers.AddressRouter
import io.github.emikaelsilveira.application.web.routers.Router
import io.github.emikaelsilveira.application.web.routers.UserRouter
import io.github.emikaelsilveira.domain.exceptions.NotFoundException
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400
import org.eclipse.jetty.http.HttpStatus.INTERNAL_SERVER_ERROR_500
import org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.core.qualifier.named

class AppConfig : KoinComponent {

    init {
        setupDependencyInjection()
    }

    companion object {
        private const val APPLICATION_PORT = 7000
    }

    private val routers: List<Router> by inject(named("routers"))

    fun setup(): Javalin {
        return startServer {
            stopKoin()
        }
    }

    private fun setupDependencyInjection() {
        startKoin {
            modules(
                listOf(
                    configModule,
                    dataBaseModule,
                    repositoryModule,
                    serviceModule,
                    routerModule,
                    controllerModule,
                    providerModule
                )
            )
        }
    }

    private fun startServer(shutdown: () -> Unit): Javalin {
        return Javalin.create { config ->
            config.enableCorsForAllOrigins()
            config.showJavalinBanner = false
        }.events { event ->
            event.serverStopped(shutdown)
        }.also { app ->
            app.routes { routers.forEach { it.register() } }

            // FIXME - Create a handler to controller exceptions on application
            app.exception(NotFoundException::class.java) { exception, ctx ->
                ctx.status(NOT_FOUND_404)
                exception.message?.let { ctx.result(it) }
            }

            app.exception(RuntimeException::class.java) { exception, ctx ->
                ctx.status(INTERNAL_SERVER_ERROR_500)
                exception.message?.let { ctx.result(it) }
            }

            app.exception(Exception::class.java) { exception, ctx ->
                ctx.status(BAD_REQUEST_400)
                exception.message?.let { ctx.result(it) }
            }

            JavalinJackson.configure(objectMapper)
            app.start(APPLICATION_PORT)
        }
    }
}
