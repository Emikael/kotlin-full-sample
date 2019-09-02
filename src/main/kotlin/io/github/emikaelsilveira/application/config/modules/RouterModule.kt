package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.application.web.routers.AddressRouter
import io.github.emikaelsilveira.application.web.routers.ControllerRouteHandler
import io.github.emikaelsilveira.application.web.routers.UserRouter
import org.koin.core.module.Module
import org.koin.dsl.module

val routerModule: Module = module {
    single { ControllerRouteHandler() }
    single { UserRouter(get(), get()) }
    single { AddressRouter(get(), get()) }
}
