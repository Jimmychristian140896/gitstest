package com.jimmy.basecompose.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = ""
)