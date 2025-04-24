package com.jimmy.basecompose.domain.model

import kotlinx.serialization.SerialName

data class Article(
    val authors: List<Author>,
    val events: List<Event>? = null,
    val featured: Boolean? = null,
    val id: Int,
    val imageUrl: String,
    val launches: List<Launch>? = null,
    val newsSite: String,
    val publishedAt: String,
    val summary: String,
    val title: String,
    val updatedAt: String,
    val url: String,
    val type: ArticleType
)