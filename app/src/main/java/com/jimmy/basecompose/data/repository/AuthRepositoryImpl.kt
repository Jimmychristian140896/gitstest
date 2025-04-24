package com.jimmy.basecompose.data.repository

import android.content.Context
import com.auth0.android.authentication.AuthenticationAPIClient
import com.jimmy.basecompose.core.auth.Auth0Config
import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.data.local.datastore.SessionManager
import com.jimmy.basecompose.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val sessionManager: SessionManager,
    private val authClient: AuthenticationAPIClient
): AuthRepository {
    override suspend fun login(username: String, password: String): Result<Empty, DataError> {
        try {
            val credentials =
                authClient.login(username, password, "Username-Password-Authentication").validateClaims().await()
            sessionManager.saveIsLogin(true)
            sessionManager.saveUser(credentials.user.name ?: "")
            return Result.Success(Empty())
        }catch (e: Exception) {
            return Result.Error(DataError.HttpError.CONFLICT_LOGIN)
        }

    }

    override suspend fun register(
        name: String,
        username: String,
        password: String
    ): Result<Empty, DataError> {
        try {
            authClient.signUp(email = username, password = password,
                    username = username, connection = "Username-Password-Authentication",
                ).addParameter("name", name).validateClaims().await()
            return Result.Success(Empty())
        }catch (e: Exception) {
            return Result.Error(DataError.HttpError.CONFLICT_SIGN_UP)
        }
    }

    override suspend fun getName(): Result<String, DataError> {
        return Result.Success(sessionManager.getUser() ?: "")
    }

    override suspend fun logout(): Result<Empty, DataError> {
        try {
            sessionManager.clearSession()
            return Result.Success(Empty())
        }catch (e: Exception) {
            return Result.Error(DataError.HttpError.UNKNOWN)
        }
    }
}