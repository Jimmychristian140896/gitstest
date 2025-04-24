package com.jimmy.basecompose.presentation.listarticle

import com.jimmy.basecompose.core.data.UiText
import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.domain.model.ArticleType

data class ListArticleState(
    val type: ArticleType = ArticleType.ARTICLE,
    val isLoading: Boolean = false,
    val isLoadingArticle: Boolean = false,
    val errorArticle: UiText? = null,
    val articles: List<Article> = emptyList(),
    val filter: List<String> = emptyList(),
    val sort: SortType? = null,
    val search: String? = null,
    val sites: List<String> = emptyList(),
    val isShowFilterDialog: Boolean = false,
    val isShowSortDialog: Boolean = false,
)

enum class SortType {
    ASC,
    DESC;

    fun getDisplayName(): String {
        return when (this) {
            ASC -> "Oldest"
            DESC -> "Newest"
        }

    }
}