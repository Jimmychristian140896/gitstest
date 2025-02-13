package com.jimmy.basecompose.core.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

suspend fun String.showSnackbar() {
    SnackBarController.sendEvent(SnackBarEvent(this))
}

suspend fun String.showSnackbar(action: SnackBarAction) {
    SnackBarController.sendEvent(SnackBarEvent(this, action))
}

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction ?= null
)

data class SnackBarAction(
    val name: String,
    val action: () -> Unit
)

object SnackBarController {

    private val _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }
}