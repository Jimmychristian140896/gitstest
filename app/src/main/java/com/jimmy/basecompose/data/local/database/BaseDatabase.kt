package com.jimmy.basecompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jimmy.basecompose.data.local.converter.ArticleConverter
import com.jimmy.basecompose.data.local.dao.ArticleDao
import com.jimmy.basecompose.data.local.entity.ArticleEntity

@Database(
    entities = [
        ArticleEntity::class
    ],
    version = 4,
)
@TypeConverters(ArticleConverter::class)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}