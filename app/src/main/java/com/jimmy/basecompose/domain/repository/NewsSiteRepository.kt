package com.jimmy.basecompose.domain.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.domain.model.Article

interface NewsSiteRepository {
    suspend fun getNewsSite(): Result<List<String>, DataError>

}