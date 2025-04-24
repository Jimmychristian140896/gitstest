package com.jimmy.basecompose.di

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.jimmy.basecompose.R
import com.jimmy.basecompose.core.network.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {
    singleOf(::provideAuth0)
    singleOf(::provideAuthenticationAPIClient)
}

fun provideAuth0(context: Context) = Auth0(
    domain = context.getString(R.string.com_auth0_domain),
    clientId = context.getString(R.string.com_auth0_client_id)
)

fun provideAuthenticationAPIClient(auth0: Auth0) = AuthenticationAPIClient(
    auth0
)


