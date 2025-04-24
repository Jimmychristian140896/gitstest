package com.jimmy.basecompose.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class SessionManager {
    private val Context.dataStore by preferencesDataStore(name = "session")

    private val KEY_LOGIN_TIME = longPreferencesKey("login_time")
    private val KEY_IS_LOGIN = booleanPreferencesKey("is_login")
    private val KEY_USER = stringPreferencesKey("user")

    suspend fun saveLoginTime(context: Context, time: Long) {
        context.dataStore.edit {
            it[KEY_LOGIN_TIME] = time
        }
    }

    suspend fun getLoginTime(context: Context): Long? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_LOGIN_TIME]
    }

    suspend fun saveIsLogin(context: Context, isLogin: Boolean) {
        context.dataStore.edit {
            it[KEY_IS_LOGIN] = isLogin
        }
    }

    suspend fun getIsLogin(context: Context): Boolean? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_IS_LOGIN]
    }

    suspend fun saveUser(context: Context, user: String) {
        context.dataStore.edit {
            it[KEY_USER] = user
        }
    }

    suspend fun getUser(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_USER]
    }

    suspend fun clearSession(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}