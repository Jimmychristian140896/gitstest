package com.jimmy.basecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.domain.repository.SessionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionRepository: SessionRepository
): ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        initLoading()
    }

    private fun initLoading() {
        viewModelScope.launch {


            sessionRepository.getLoginTime()
                .onSuccess {loginTime ->
                    val isSessionExpired = loginTime == null || (System.currentTimeMillis() - loginTime) > 10 * 60 * 1000

                    if (isSessionExpired) {
                        _state.update { it.copy(
                            isLoading = true,
                            isLoggedIn = false
                        ) }

                    } else {
                        _state.update { it.copy(
                            isLoading = true,
                            isLoggedIn = sessionRepository.isLogin()
                        ) }
                    }
                    delay(1000L)
                    _state.update { it.copy(
                        isLoading = false,
                    ) }
                }



        }
    }
}