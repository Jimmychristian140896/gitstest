package com.jimmy.basecompose.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimmy.basecompose.core.data.UiText
import com.jimmy.basecompose.core.data.asUiText
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.core.util.isValidEmail
import com.jimmy.basecompose.core.util.isValidPassword
import com.jimmy.basecompose.domain.model.User
import com.jimmy.basecompose.domain.repository.AuthRepository
import com.jimmy.basecompose.domain.repository.SessionRepository
import com.jimmy.basecompose.presentation.login.LoginEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val sessionRepository: SessionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterState()
        )

    private val _eventChannel = Channel<RegisterEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when(action) {
            is RegisterAction.OnLoginClicked -> {
                viewModelScope.launch { 
                    _eventChannel.trySend(RegisterEvent.NavigateToLogin)
                }
            }
            is RegisterAction.OnRegisterClicked -> {
                register()
            }

            is RegisterAction.OnNameChanged -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        name = action.name,
                        nameError = null
                    ) }
                }
            }

            is RegisterAction.OnUsernameChanged -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        username = action.username,
                        usernameError = null
                    ) }
                }
            }
            is RegisterAction.OnPasswordChanged -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        password = action.password,
                        passwordError = null,
                        repeatPasswordError = null
                    ) }
                }
            }

            RegisterAction.OnPasswordVisibleChange -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        passwordVisible = !it.passwordVisible
                    ) }
                }
            }
            is RegisterAction.OnRepeatPasswordChanged -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        repeatPassword = action.password,
                        repeatPasswordError = null,
                        passwordError = null
                    ) }
                }
            }

            RegisterAction.OnRepeatPasswordVisibleChange -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        repeatPasswordVisible = !it.repeatPasswordVisible
                    ) }
                }
            } 
            else -> {
                
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            var isValid = true
            if(_state.value.name.isEmpty()) {
                _state.update { it.copy(
                    nameError = UiText.DynamicString("Name cannot be empty")
                ) }
                isValid = false
            }
            if(_state.value.username.isEmpty()) {
                _state.update { it.copy(
                    usernameError = UiText.DynamicString("Email address cannot be empty")
                ) }
                isValid = false
            }
            if(!_state.value.username.isValidEmail()) {
                _state.update { it.copy(
                    usernameError = UiText.DynamicString("Email address not valid")
                ) }
                isValid = false
            }
            if(_state.value.password.isEmpty()) {
                _state.update { it.copy(
                    passwordError = UiText.DynamicString("Password cannot be empty")
                ) }
                isValid = false
            }
            if(!_state.value.password.isValidPassword()) {
                _state.update { it.copy(
                    passwordError = UiText.DynamicString("Password not valid")
                ) }
                isValid = false
            }
            if(_state.value.repeatPassword.isEmpty()) {
                _state.update { it.copy(
                    repeatPasswordError = UiText.DynamicString("Repeat Password cannot be empty")
                ) }
                isValid = false
            }
            if(_state.value.repeatPassword != _state.value.password) {
                _state.update { it.copy(
                    repeatPasswordError = UiText.DynamicString("Password and Repeat Password must be same")
                ) }
                isValid = false
            }
            if(isValid) {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                authRepository.register(
                    name = _state.value.name,
                    username = _state.value.username,
                    password = _state.value.password

                ).onSuccess {
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
                        _eventChannel.trySend(RegisterEvent.OnLoginSuccess)
                    }.onFailure {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _eventChannel.trySend(RegisterEvent.OnLoginFailed(it.asUiText()))
                    }
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _eventChannel.trySend(RegisterEvent.OnRegisterFailed(it.asUiText()))
                }
            }

        }
    }

}