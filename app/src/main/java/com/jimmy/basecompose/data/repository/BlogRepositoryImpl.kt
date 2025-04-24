package com.jimmy.basecompose.data.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.core.network.constructRoute
import com.jimmy.basecompose.core.network.safeCall
import com.jimmy.basecompose.data.local.dao.ArticleDao
import com.jimmy.basecompose.data.remote.dto.ArticleDto
import com.jimmy.basecompose.data.remote.dto.BasePagingResponse
import com.jimmy.basecompose.data.remote.dto.BlogDto
import com.jimmy.basecompose.domain.mappers.toArticle
import com.jimmy.basecompose.domain.mappers.toArticleEntity
import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.domain.repository.BlogRepository
import com.jimmy.basecompose.presentation.listarticle.SortType
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class BlogRepositoryImpl(
    private val client: HttpClient,
    private val articleDao: ArticleDao
): BlogRepository {
    override suspend fun getBlogs(
        saveToLocal: Boolean,
        search: String?,
        filter: List<String>?,
        sort: SortType?
    ): Result<List<Article>, DataError> {
        val result = safeCall<BasePagingResponse<BlogDto>> {
            client.get(
                urlString = constructRoute("/blogs")
            ) {
                search?.let {
                    parameter("title_contains", search)
                }
                filter?.let {
                    parameter("news_site", filter.joinToString(","))
                }
                sort?.let {
                    parameter("ordering", if(it == SortType.ASC) "published_at" else "-published_at")
                }
            }
        }
        when(result) {
            is Result.Error -> {
                return Result.Error(result.error)
            }
            is Result.Success -> {

                val articles = result.data.results.map { it.toArticle() }
                if(saveToLocal) {
                    articleDao.deleteArticlesByType(ArticleType.BLOG)
                    articleDao.upsertArticles(articles.map { it.toArticleEntity() })
                }
                return Result.Success(articles)
            }
        }
    }
    override suspend fun getBlogById(id: Int): Result<Article, DataError> {
        val result = safeCall<BlogDto> {
            client.get(
                urlString = constructRoute("/blogs/$id")
            ) {

            }
        }
        when(result) {
            is Result.Error -> {
                return Result.Error(result.error)
            }
            is Result.Success -> {
                return Result.Success(result.data.toArticle() )
            }
        }
    }

    override suspend fun getLastLoadedBlogs(): Result<List<Article>, DataError> {
        val articles = articleDao.getArticlesByType(ArticleType.BLOG).map { it.toArticle() }
        return Result.Success(articles)
    }
}