package com.jimmy.basecompose.di

import androidx.room.Room
import com.jimmy.basecompose.data.local.database.BaseDatabase
import com.jimmy.basecompose.data.local.datastore.RecentSearchManager
import com.jimmy.basecompose.data.local.datastore.SessionManager
import com.jimmy.basecompose.data.repository.UserRepositoryImpl
import com.jimmy.basecompose.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::RecentSearchManager)
    singleOf(::SessionManager)
}