package com.jimmy.basecompose.presentation.listarticle

import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.presentation.home.HomeEvent

sealed interface ListArticleEvent {
    data class OnArticleClick(val article: Article) : ListArticleEvent
    data class OnSearch(val search: String) : ListArticleEvent
    data object NavigateBack: ListArticleEvent

}