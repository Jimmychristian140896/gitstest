package com.jimmy.basecompose.core.auth

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage

object Auth0Config {
    lateinit var auth0: Auth0
    lateinit var authentication: AuthenticationAPIClient
    lateinit var storage: SharedPreferencesStorage
    lateinit var manager: CredentialsManager

    fun init(context: Context, domain: String, clientId: String) {
        auth0 = Auth0(clientId, domain)
        authentication = AuthenticationAPIClient(auth0)
        storage = SharedPreferencesStorage(context)
        manager = CredentialsManager(authentication, storage)
    }

    fun init(context: Context) {
        auth0 = Auth0(context)
        //auth0.isOIDCConformant = true
    }
}