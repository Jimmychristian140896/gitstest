package com.jimmy.basecompose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimmy.basecompose.core.data.asUiText
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.domain.model.User
import com.jimmy.basecompose.domain.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
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
                    _state.value = _state.value.copy(
                        username = action.username
                    )
                }
            }
            is LoginAction.OnPasswordChanged -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        password = action.password
                    )
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            userRepository.upsertUser(
                User(
                    username = _state.value.username,
                    password = _state.value.password
                )
            ).onSuccess {
                _state.value = _state.value.copy(
                    isLoading = false
                )
                _eventChannel.trySend(LoginEvent.OnLoginSuccess)
            }.onFailure {
                _state.value = _state.value.copy(
                    isLoading = false
                )
                _eventChannel.trySend(LoginEvent.OnLoginFailed(it.asUiText()))
            }

        }
    }
}