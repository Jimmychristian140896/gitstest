package com.jimmy.basecompose.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Social(
    val bluesky: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val x: String,
    val youtube: String
)