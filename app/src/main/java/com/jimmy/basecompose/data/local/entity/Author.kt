package com.jimmy.basecompose.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthorEntity(
    val name: String,
    val socials: SocialEntity?
)