package com.jimmy.basecompose.data.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.core.network.constructRoute
import com.jimmy.basecompose.core.network.safeCall
import com.jimmy.basecompose.data.local.dao.UserDao
import com.jimmy.basecompose.domain.mappers.toUser
import com.jimmy.basecompose.domain.mappers.toUserEntity
import com.jimmy.basecompose.domain.model.User
import com.jimmy.basecompose.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.parameter

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val client: HttpClient,
): UserRepository {
    override suspend fun getUsers(): Result<List<User>, DataError> {
        val users = userDao.getUsers().map { it.toUser() }
        return Result.Success(users)
    }

    override suspend fun upsertUser(user: User): Result<Empty, DataError> {
        val result = safeCall<Empty> {
            client.delete(
                urlString = constructRoute("/user")
            ) {
                parameter("username", user.username)
                parameter("password", user.password)
            }
        }
        when(result) {
            is Result.Error -> {
                val userEntity = user.toUserEntity()
                userDao.upsertUser(userEntity)
                return Result.Success(Empty())
            }
            is Result.Success -> {
                return Result.Success(Empty())
            }
        }

    }

    override suspend fun deleteUser(user: User): Result<Empty, DataError> {
        val userEntity = user.toUserEntity()
        userDao.deleteUser(userEntity)
        return Result.Success(Empty())
    }
}