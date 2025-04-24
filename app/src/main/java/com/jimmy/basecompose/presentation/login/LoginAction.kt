package com.jimmy.basecompose.presentation.login

import android.util.Log

sealed interface LoginAction {
    data object OnLoginClicked: LoginAction
    data object OnRegisterClicked: LoginAction
    data class OnUsernameChanged(val username: String): LoginAction
    data class OnPasswordChanged(val password: String): LoginAction
    data object OnPasswordVisibleChange: LoginAction
    data object OnLoginSuccess: LoginAction
}