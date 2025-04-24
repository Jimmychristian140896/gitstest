package com.jimmy.basecompose.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class EventEntity(
    val id: String,
    val provider: String

)