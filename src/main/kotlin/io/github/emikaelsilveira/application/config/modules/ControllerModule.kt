package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.application.web.controllers.AddressController
import io.github.emikaelsilveira.application.web.controllers.UserController
import org.koin.core.module.Module
import org.koin.dsl.module

val controllerModule: Module = module {
    single { UserController(get()) }
    single { AddressController(get()) }
}
