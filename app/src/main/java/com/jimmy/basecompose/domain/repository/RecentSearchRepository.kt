package com.jimmy.basecompose.domain.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result

interface RecentSearchRepository {
    suspend fun getRecentSearches(): Result<List<String>, DataError>
    suspend fun addRecentSearch(search: String): Result<Empty, DataError>
    suspend fun deleteRecentSearch(search: String): Result<Empty, DataError>

}