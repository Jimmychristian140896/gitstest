package com.jimmy.basecompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jimmy.basecompose.data.local.converter.ArticleConverter
import com.jimmy.basecompose.data.local.dao.ArticleDao
import com.jimmy.basecompose.data.local.dao.UserDao
import com.jimmy.basecompose.data.local.entity.ArticleEntity
import com.jimmy.basecompose.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ArticleEntity::class
    ],
    version = 3,
)
@TypeConverters(ArticleConverter::class)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao
}