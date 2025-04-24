package com.jimmy.basecompose.data.repository

import android.content.Context
import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.data.local.datastore.RecentSearchManager
import com.jimmy.basecompose.domain.repository.RecentSearchRepository

class RecentSearchRepositoryImpl(
    private val recentSearchManager: RecentSearchManager
): RecentSearchRepository {
    override suspend fun getRecentSearches(): Result<List<String>, DataError> {
        return Result.Success(recentSearchManager.getRecentSearches())
    }

    override suspend fun addRecentSearch(search: String): Result<Empty, DataError> {
        recentSearchManager.saveSearch(search)
        return Result.Success(Empty())

    }

    override suspend fun deleteRecentSearch(search: String): Result<Empty, DataError> {
        recentSearchManager.deleteSearch(search)
        return Result.Success(Empty())
    }
}