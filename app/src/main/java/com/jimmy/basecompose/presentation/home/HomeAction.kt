package com.jimmy.basecompose.presentation.home

import com.jimmy.basecompose.domain.model.Article

sealed interface HomeAction {
    data class OnArticleClick(val article: Article) : HomeAction
    data object OnSeeMoreArticleClick : HomeAction
    data object OnSeeMoreBlogClick : HomeAction
    data object OnSeeMoreReportClick : HomeAction
}