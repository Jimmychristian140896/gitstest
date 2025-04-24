package com.jimmy.basecompose.navigation

import com.jimmy.basecompose.domain.model.ArticleType
import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Login: Route()
    @Serializable
    data object Register: Route()
    @Serializable
    data object Home: Route()
    @Serializable
    data class List(val type: ArticleType): Route()
    @Serializable
    data class Detail(val type: ArticleType, val id: Int): Route()
    @Serializable
    data class Seach(val search: String): Route()
}