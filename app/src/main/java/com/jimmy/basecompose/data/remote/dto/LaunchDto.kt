package com.jimmy.basecompose.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDto(
    @SerialName("launch_id")
    val launchId: String,
    @SerialName("provider")
    val provider: String
)