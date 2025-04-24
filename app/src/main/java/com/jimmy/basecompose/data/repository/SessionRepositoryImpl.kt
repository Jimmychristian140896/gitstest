package com.jimmy.basecompose.data.repository

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jimmy.basecompose.core.data.DataError
import com.jimmy.basecompose.core.data.Empty
import com.jimmy.basecompose.core.data.Result
import com.jimmy.basecompose.data.local.datastore.SessionManager
import com.jimmy.basecompose.data.local.worker.AutoLogoutWorker
import com.jimmy.basecompose.domain.repository.SessionRepository
import java.util.concurrent.TimeUnit

class SessionRepositoryImpl(
    private val context: Context,
    private val sessionManager: SessionManager
): SessionRepository {
    private fun scheduleAutoLogout() {
        val workRequest = OneTimeWorkRequestBuilder<AutoLogoutWorker>()
            .setInitialDelay(10, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "auto_logout",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    override suspend fun saveLoginTime() {
        val now = System.currentTimeMillis()
        sessionManager.saveIsLogin(true)
        sessionManager.saveLoginTime(now)
        scheduleAutoLogout()
    }

    override suspend fun getLoginTime(): Result<Long?, DataError> {
        return Result.Success(sessionManager.getLoginTime())
    }

    override suspend fun isLogin(): Boolean {
        return sessionManager.getIsLogin() ?: false
    }


}