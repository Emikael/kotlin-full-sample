package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.application.config.database.DatabaseConfig
import org.koin.core.module.Module
import org.koin.dsl.module

val dataBaseModule: Module = module {
    single { DatabaseConfig().dataSource }
}
