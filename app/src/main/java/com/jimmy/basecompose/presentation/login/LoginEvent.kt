package com.jimmy.basecompose.presentation.login

import com.jimmy.basecompose.core.data.UiText

sealed interface LoginEvent {
    data object NavigateToRegister: LoginEvent
    data object OnLoginSuccess: LoginEvent
    data class OnLoginFailed(val message: UiText): LoginEvent
}