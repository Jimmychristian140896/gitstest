package com.jimmy.basecompose.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsSiteDto(
    @SerialName("news_sites")
    val newsSites: List<String>,
    @SerialName("version")
    val version: String
)