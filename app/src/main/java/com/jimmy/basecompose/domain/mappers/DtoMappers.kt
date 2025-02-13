package com.jimmy.basecompose.domain.mappers

import com.jimmy.basecompose.data.remote.dto.UserDto
import com.jimmy.basecompose.domain.model.User

fun UserDto.toUser() = User(
    username = username,
    password = password
)
fun User.toUserDto() = UserDto(
    username = username,
    password = password
)