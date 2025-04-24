package com.jimmy.basecompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.domain.model.Author
import com.jimmy.basecompose.domain.model.Event
import com.jimmy.basecompose.domain.model.Launch

@Entity(
    tableName = "articles",
    primaryKeys = [
        "id",
        "type"
    ],
)
class ArticleEntity(
    val id: Int,
    val authors: List<AuthorEntity>,
    val events: List<EventEntity>? = null,
    val featured: Boolean? = null,
    val imageUrl: String,
    val launches: List<LaunchEntity>? = null,
    val newsSite: String,
    val publishedAt: String,
    val summary: String,
    val title: String,
    val updatedAt: String,
    val url: String,
    val type: ArticleType
)