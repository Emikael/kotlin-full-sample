package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.domain.services.AddressService
import io.github.emikaelsilveira.domain.services.UserService
import io.github.emikaelsilveira.domain.services.implementations.AddressServiceImpl
import io.github.emikaelsilveira.domain.services.implementations.UserServiceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val serviceModule: Module = module {
    single { UserServiceImpl(get()) as UserService }
    single { AddressServiceImpl(get(), get()) as AddressService }
}
