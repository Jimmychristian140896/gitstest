package com.jimmy.basecompose.presentation.searcharticle

sealed interface SearchArticleEvent {
    data object GoBack : SearchArticleEvent
    data class OnSearch(val search: String) : SearchArticleEvent
}