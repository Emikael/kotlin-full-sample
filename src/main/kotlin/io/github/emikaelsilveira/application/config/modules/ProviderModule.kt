package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.domain.providers.AddressProvider
import io.github.emikaelsilveira.resource.viacep.consumers.ViaCepConsumer
import org.koin.core.module.Module
import org.koin.dsl.module

val providerModule: Module = module {
    single { ViaCepConsumer() as AddressProvider }
}
