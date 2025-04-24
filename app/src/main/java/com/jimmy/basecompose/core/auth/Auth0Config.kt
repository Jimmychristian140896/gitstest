package com.jimmy.basecompose.core.auth

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage

object Auth0Config {
    lateinit var auth0: Auth0

    fun init(domain: String, clientId: String) {
        auth0 = Auth0(clientId, domain)
    }
}