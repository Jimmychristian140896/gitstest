package com.jimmy.basecompose.presentation.register

import com.jimmy.basecompose.core.data.UiText
import com.jimmy.basecompose.presentation.login.LoginEvent

sealed interface RegisterEvent {
    data object NavigateToLogin: RegisterEvent
    data object OnRegisterSuccess: RegisterEvent
    data class OnRegisterFailed(val message: UiText): RegisterEvent
    data object OnLoginSuccess: RegisterEvent
    data class OnLoginFailed(val message: UiText): RegisterEvent

}