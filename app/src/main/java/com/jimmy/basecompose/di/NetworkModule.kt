package com.jimmy.basecompose.di

import com.jimmy.basecompose.core.network.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::provideHttpClient)
}


fun provideHttpClient() = HttpClientFactory.create()