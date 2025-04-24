package com.jimmy.basecompose.domain.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.presentation.listarticle.SortType

interface ArticleRepository {
    suspend fun getArticles(
        saveToLocal: Boolean = false,
        search: String? = null,
        filter: List<String>? = null,
        sort: SortType? = null
    ): Result<List<Article>, DataError>
    suspend fun getArticleById(id: Int): Result<Article, DataError>

    suspend fun getLastLoadedArticles(): Result<List<Article>, DataError>
}