package com.jimmy.basecompose

import android.app.Application
import com.jimmy.basecompose.core.auth.Auth0Config
import com.jimmy.basecompose.di.appModule
import com.jimmy.basecompose.di.authModule
import com.jimmy.basecompose.di.databaseModule
import com.jimmy.basecompose.di.networkModule
import com.jimmy.basecompose.di.repositoryModule
import com.jimmy.basecompose.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                appModule,
                databaseModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                authModule
            )
        }
    }
}