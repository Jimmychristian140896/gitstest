package com.jimmy.basecompose.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val name: String,
    val socials: Social?
)