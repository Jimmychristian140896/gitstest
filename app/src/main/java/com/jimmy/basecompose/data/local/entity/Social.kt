package com.jimmy.basecompose.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class SocialEntity(
    val bluesky: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val x: String,
    val youtube: String
)