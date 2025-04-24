package com.jimmy.basecompose.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class LaunchEntity(
    val id: String,
    val provider: String
)
