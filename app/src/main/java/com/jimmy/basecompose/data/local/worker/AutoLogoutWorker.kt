package com.jimmy.basecompose.data.local.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jimmy.basecompose.R
import com.jimmy.basecompose.data.local.datastore.SessionManager
import com.jimmy.basecompose.domain.repository.AuthRepository
import org.koin.java.KoinJavaComponent.inject

class AutoLogoutWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    // val sessionManager: SessionManager by inject(SessionManager::class.java)
    private val authRepository: AuthRepository by inject(AuthRepository::class.java)

    override suspend fun doWork(): Result {
        /*val loginTime = sessionManager.getLoginTime(applicationContext) ?: return Result.failure()

        val currentTime = System.currentTimeMillis()
        val diff = currentTime - loginTime

        if (diff >= 10 * 60 * 1000) { // 10 menit
            sessionManager.clearSession(applicationContext)
            sendLogoutNotification()
            // opsional: clear local token, db, dsb
        }*/
        authRepository.logout()
        sendLogoutNotification()

        return Result.success()
    }

    private fun sendLogoutNotification() {
        val channelId = "logout_channel"

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Logout Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Your account has been logged out")
            .setContentText("You have been automatically logged out after 10 minutes.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1001, notification)
    }
}
