package com.jimmy.basecompose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimmy.basecompose.core.data.UiText
import com.jimmy.basecompose.core.data.asUiText
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.domain.model.User
import com.jimmy.basecompose.domain.repository.AuthRepository
import com.jimmy.basecompose.domain.repository.SessionRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionRepository: SessionRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when(action) {
            is LoginAction.OnLoginClicked -> {
                login()
            }

            is LoginAction.OnUsernameChanged -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        username = action.username,
                        usernameError = null
                    ) }
                }
            }
            is LoginAction.OnPasswordChanged -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        password = action.password,
                        passwordError = null
                    ) }
                }
            }

            LoginAction.OnPasswordVisibleChange -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        passwordVisible = !it.passwordVisible
                    ) }
                }
            }

            LoginAction.OnRegisterClicked -> {
                viewModelScope.launch {
                    _eventChannel.trySend(LoginEvent.NavigateToRegister)
                }
            }

            LoginAction.OnLoginSuccess -> {
                viewModelScope.launch {
                    _eventChannel.trySend(LoginEvent.OnLoginSuccess)
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            var isValid = true
            if(_state.value.username.isEmpty()) {
                _state.update { it.copy(
                    usernameError = UiText.DynamicString("Email address cannot be empty")
                ) }
                isValid = false
            }
            if(_state.value.password.isEmpty()) {
                _state.update { it.copy(
                    passwordError = UiText.DynamicString("Password cannot be empty")
                ) }
                isValid = false
            }
            if(isValid) {

                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                authRepository.login(
                    username = _state.value.username,
                    password = _state.value.password
                ).onSuccess {
                    sessionRepository.saveLoginTime()
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _eventChannel.trySend(LoginEvent.OnLoginSuccess)
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _eventChannel.trySend(LoginEvent.OnLoginFailed(it.asUiText()))
                }
            }
        }
    }
}