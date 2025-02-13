package com.jimmy.basecompose.di

import com.jimmy.basecompose.data.repository.UserRepositoryImpl
import com.jimmy.basecompose.domain.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}