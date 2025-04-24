package com.jimmy.basecompose.presentation.detailarticle

sealed interface DetailArticleEvent {
    data object NavigateBack : DetailArticleEvent

}