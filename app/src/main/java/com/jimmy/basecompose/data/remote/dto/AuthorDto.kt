package com.jimmy.basecompose.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDto(
    @SerialName("name")
    val name: String,
    @SerialName("socials")
    val socials: SocialDto?
)