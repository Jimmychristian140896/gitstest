package com.jimmy.basecompose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jimmy.basecompose.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Upsert
    suspend fun upsertUser(user: UserEntity)
    @Upsert
    suspend fun upsertUsers(users: List<UserEntity>)
    @Delete
    suspend fun deleteUser(user: UserEntity)
    @Query("DELETE FROM users WHERE username IN(:users)")
    suspend fun deleteUsers(users: List<String>)

}