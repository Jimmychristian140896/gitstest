package com.jimmy.basecompose.presentation.searcharticle

sealed interface SearchArticleAction {

    data class Search(val search: String): SearchArticleAction
    data object SearchClicked: SearchArticleAction
    data object GoBack: SearchArticleAction
}