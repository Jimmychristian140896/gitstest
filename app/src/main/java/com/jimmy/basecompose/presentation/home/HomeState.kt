package com.jimmy.basecompose.presentation.home

import com.jimmy.basecompose.core.data.UiText
import com.jimmy.basecompose.domain.model.Article

data class HomeState(
    val isLoadingArticle: Boolean = false,
    val errorArticle: UiText? = null,
    val articles: List<Article> = emptyList(),
    val isLoadingBlog: Boolean = false,
    val errorBlog: UiText? = null,
    val blogs: List<Article> = emptyList(),
    val isLoadingReport: Boolean = false,
    val errorReport: UiText? = null,
    val reports: List<Article> = emptyList(),
    val greeting: String = "",
    val user: String = "",
)