package com.jimmy.basecompose.data.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.core.network.constructRoute
import com.jimmy.basecompose.core.network.safeCall
import com.jimmy.basecompose.data.remote.dto.ArticleDto
import com.jimmy.basecompose.data.remote.dto.BasePagingResponse
import com.jimmy.basecompose.data.remote.dto.NewsSiteDto
import com.jimmy.basecompose.domain.mappers.toArticle
import com.jimmy.basecompose.domain.repository.NewsSiteRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsSiteRepositoryImpl(
    private val client: HttpClient
): NewsSiteRepository {
    override suspend fun getNewsSite(): Result<List<String>, DataError> {
        val result = safeCall<NewsSiteDto> {
            client.get(
                urlString = constructRoute("/info")
            ) {

            }
        }
        when(result) {
            is Result.Error -> {
                return Result.Error(result.error)
            }
            is Result.Success -> {
                return Result.Success(result.data.newsSites)
            }
        }
    }
}