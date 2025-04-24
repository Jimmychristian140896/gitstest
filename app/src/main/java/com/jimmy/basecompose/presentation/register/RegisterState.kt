package com.jimmy.basecompose.presentation.register

import com.jimmy.basecompose.core.data.UiText

data class RegisterState(
    val isLoading: Boolean = false,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val repeatPassword: String = "",
    val repeatPasswordVisible: Boolean = false,

    val usernameError: UiText? = null,
    val passwordError: UiText? = null,
    val repeatPasswordError: UiText? = null,
    val nameError: UiText? = null,
)