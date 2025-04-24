package com.jimmy.basecompose.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialDto(
    @SerialName("bluesky")
    val bluesky: String,
    @SerialName("instagram")
    val instagram: String,
    @SerialName("linkedin")
    val linkedin: String,
    @SerialName("mastodon")
    val mastodon: String,
    @SerialName("x")
    val x: String,
    @SerialName("youtube")
    val youtube: String
)