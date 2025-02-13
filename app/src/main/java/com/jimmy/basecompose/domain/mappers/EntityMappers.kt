package com.jimmy.basecompose.domain.mappers

import com.jimmy.basecompose.data.local.entity.UserEntity
import com.jimmy.basecompose.data.remote.dto.UserDto
import com.jimmy.basecompose.domain.model.User

fun UserEntity.toUser() = User(
    username = username,
    password = password
)
fun User.toUserEntity() = UserEntity(
    username = username,
    password = password
)