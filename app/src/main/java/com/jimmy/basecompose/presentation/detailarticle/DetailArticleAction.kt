package com.jimmy.basecompose.presentation.detailarticle

sealed interface DetailArticleAction {
    data object NavigateBack : DetailArticleAction

}