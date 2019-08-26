package io.github.emikaelsilveira.application

import io.github.emikaelsilveira.application.config.AppConfig
import io.github.emikaelsilveira.application.config.modules.configModule
import io.github.emikaelsilveira.application.config.modules.controllerModule
import io.github.emikaelsilveira.application.config.modules.dataBaseModule
import io.github.emikaelsilveira.application.config.modules.repositoryModule
import io.github.emikaelsilveira.application.config.modules.routerModule
import io.github.emikaelsilveira.application.config.modules.serviceModule
import org.koin.core.context.startKoin

fun main() {
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
    AppConfig().init()
}
