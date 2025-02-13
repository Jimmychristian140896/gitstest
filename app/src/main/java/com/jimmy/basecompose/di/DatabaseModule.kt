package com.jimmy.basecompose.di

import android.content.Context
import androidx.room.Room
import com.jimmy.basecompose.data.local.database.BaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::provideDatabase)
    singleOf(::provideUserDao)
}

fun provideDatabase(context: Context) = Room.databaseBuilder(
    context,
    BaseDatabase::class.java,
    "base.db"
).build()

fun provideUserDao(database: BaseDatabase) = database.userDao()