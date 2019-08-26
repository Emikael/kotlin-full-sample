package io.github.emikaelsilveira.application.config

import io.github.emikaelsilveira.application.config.modules.configModule
import io.github.emikaelsilveira.application.config.modules.controllerModule
import io.github.emikaelsilveira.application.config.modules.dataBaseModule
import io.github.emikaelsilveira.application.config.modules.repositoryModule
import io.github.emikaelsilveira.application.config.modules.routerModule
import io.github.emikaelsilveira.application.config.modules.serviceModule
import io.github.emikaelsilveira.application.web.routers.AddressRouter
import io.github.emikaelsilveira.application.web.routers.UserRouter
import io.github.emikaelsilveira.domain.exceptions.AddressNotFoundException
import io.github.emikaelsilveira.domain.exceptions.UserNotFoundException
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject

class AppConfig : KoinComponent {

    companion object {
        private const val APPLICATION_PORT = 7000
    }

    private val userRouter: UserRouter by inject()
    private val addressRouter: AddressRouter by inject()

    fun init() {
//        setupDependencyInjection()
        startServer {
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
                    controllerModule
                )
            )
        }
    }

    private fun startServer(shutdown: () -> Unit) {
        Javalin.create { config ->
            config.enableCorsForAllOrigins()
            config.showJavalinBanner = false
        }.events { event ->
            event.serverStopped(shutdown)
        }.also { app ->
            userRouter.register(app)
            addressRouter.register(app)

            app.exception(UserNotFoundException::class.java) { exception, ctx ->
                ctx.status(NOT_FOUND_404)
                exception.message?.let { ctx.result(it) }
            }

            app.exception(AddressNotFoundException::class.java) { exception, ctx ->
                ctx.status(NOT_FOUND_404)
                exception.message?.let { ctx.result(it) }
            }

            JavalinJackson.configure(objectMapper)
            app.start(APPLICATION_PORT)
        }
    }
}
