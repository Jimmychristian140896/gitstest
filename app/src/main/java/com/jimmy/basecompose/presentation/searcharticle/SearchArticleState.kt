package com.jimmy.basecompose.presentation.searcharticle

data class SearchArticleState(
    val isLoading: Boolean = false,
    val search: String = "",
    val recentSearch: List<String> = emptyList(),
)