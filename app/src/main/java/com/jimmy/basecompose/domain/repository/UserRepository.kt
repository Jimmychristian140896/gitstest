package com.jimmy.basecompose.domain.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>, DataError>
    suspend fun upsertUser(user: User): Result<Empty, DataError>
    suspend fun deleteUser(user: User): Result<Empty, DataError>
}