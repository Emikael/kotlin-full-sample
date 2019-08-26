package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.application.config.AppConfig
import org.koin.core.module.Module
import org.koin.dsl.module

val configModule: Module = module {
    single { AppConfig() }
}
