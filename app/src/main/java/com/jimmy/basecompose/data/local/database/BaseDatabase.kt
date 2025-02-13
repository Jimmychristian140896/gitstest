package com.jimmy.basecompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jimmy.basecompose.data.local.dao.UserDao
import com.jimmy.basecompose.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}