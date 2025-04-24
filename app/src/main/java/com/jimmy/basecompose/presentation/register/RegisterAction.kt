package com.jimmy.basecompose.presentation.register

import com.jimmy.basecompose.presentation.login.LoginAction

sealed interface RegisterAction {
    data object OnRegisterClicked: RegisterAction
    data object OnLoginClicked: RegisterAction
    data class OnNameChanged(val name: String): RegisterAction
    data class OnUsernameChanged(val username: String): RegisterAction
    data class OnPasswordChanged(val password: String): RegisterAction
    data object OnPasswordVisibleChange: RegisterAction
    data class OnRepeatPasswordChanged(val password: String): RegisterAction
    data object OnRepeatPasswordVisibleChange: RegisterAction
}