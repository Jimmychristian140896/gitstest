package com.jimmy.basecompose.presentation.login

import com.jimmy.basecompose.core.data.UiText

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val usernameError: UiText? = null,
    val passwordError: UiText? = null,
)