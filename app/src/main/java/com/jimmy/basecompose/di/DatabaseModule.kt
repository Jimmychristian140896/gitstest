package com.jimmy.basecompose.di

import android.content.Context
import androidx.room.Room
import com.jimmy.basecompose.data.local.database.BaseDatabase
import com.jimmy.basecompose.data.local.datastore.RecentSearchManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::provideDatabase)
    singleOf(::provideUserDao)
    singleOf(::provideArticleDao)
}

fun provideDatabase(context: Context) = Room.databaseBuilder(
    context,
    BaseDatabase::class.java,
    "base.db"
).fallbackToDestructiveMigration().build()

fun provideUserDao(database: BaseDatabase) = database.userDao()
fun provideArticleDao(database: BaseDatabase) = database.articleDao()