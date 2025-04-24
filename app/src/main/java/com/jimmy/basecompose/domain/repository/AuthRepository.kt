package com.jimmy.basecompose.domain.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.domain.model.Article

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<Empty, DataError>
    suspend fun register(name: String, username: String, password: String): Result<Empty, DataError>
    suspend fun getName(): Result<String, DataError>
    suspend fun logout(): Result<Empty, DataError>
}