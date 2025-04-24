package com.jimmy.basecompose.domain.repository

import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.domain.model.Article

interface SessionRepository {
    suspend fun saveLoginTime()
    suspend fun getLoginTime(): Result<Long?, DataError>
    suspend fun isLogin(): Boolean

}