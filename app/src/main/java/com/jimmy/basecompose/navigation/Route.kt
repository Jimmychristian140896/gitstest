package com.jimmy.basecompose.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Auth: Route()
    @Serializable
    data object Login: Route()
}