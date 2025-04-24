package com.jimmy.basecompose.presentation.detailarticle

import com.jimmy.basecompose.core.data.UiText
import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.domain.model.ArticleType

data class DetailArticleState(
    val id: Int? = null,
    val type: ArticleType? = null,
    val isLoading: Boolean = false,
    val article: Article? = null,
    val error: UiText? = null

)