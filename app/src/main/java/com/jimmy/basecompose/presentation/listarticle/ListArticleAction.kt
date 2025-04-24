package com.jimmy.basecompose.presentation.listarticle

import com.jimmy.basecompose.domain.model.Article

sealed interface ListArticleAction {
    data class OnSearchChange(val search: String) : ListArticleAction
    data class OnArticleClick(val article: Article) : ListArticleAction
    data object OnSearch : ListArticleAction
    data object OnSort : ListArticleAction
    data object OnFilter : ListArticleAction
    data class OnFilterSelected(val filter: List<String>) : ListArticleAction
    data class OnSortSelected(val sortType: SortType) : ListArticleAction
    data object OnHideFilterDialog : ListArticleAction
    data object OnHideSortDialog : ListArticleAction
    data object NavigateToSearch : ListArticleAction
    data object NavigateBack : ListArticleAction

}