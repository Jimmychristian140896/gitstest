package com.jimmy.basecompose.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("event_id")
    val eventId: Int,
    @SerialName("provider")
    val provider: String
)