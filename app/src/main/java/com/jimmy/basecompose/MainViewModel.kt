package com.jimmy.basecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        initLoading()
    }

    private fun initLoading() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            delay(1000L)

            _state.value = _state.value.copy(
                isLoading = false
            )
        }
    }
}