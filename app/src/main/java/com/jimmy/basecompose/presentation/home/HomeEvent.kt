package com.jimmy.basecompose.presentation.home

import com.jimmy.basecompose.domain.model.Article

sealed interface HomeEvent {
    data class OnArticleClick(val article: Article) : HomeEvent
    data object OnSeeMoreArticleClick : HomeEvent
    data object OnSeeMoreBlogClick : HomeEvent
    data object OnSeeMoreReportClick : HomeEvent
}