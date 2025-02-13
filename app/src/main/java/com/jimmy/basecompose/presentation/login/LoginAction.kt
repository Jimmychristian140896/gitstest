package com.jimmy.basecompose.presentation.login

import android.util.Log

sealed interface LoginAction {
    data object OnLoginClicked: LoginAction
    data class OnUsernameChanged(val username: String): LoginAction
    data class OnPasswordChanged(val password: String): LoginAction
}