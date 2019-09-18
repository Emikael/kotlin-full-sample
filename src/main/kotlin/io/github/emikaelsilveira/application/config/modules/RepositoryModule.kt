package io.github.emikaelsilveira.application.config.modules

import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.resources.repositories.AddressRepositoryImpl
import io.github.emikaelsilveira.resources.repositories.UserRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single { UserRepositoryImpl(get()) as UserRepository }
    single { AddressRepositoryImpl(get()) as AddressRepository }
}
