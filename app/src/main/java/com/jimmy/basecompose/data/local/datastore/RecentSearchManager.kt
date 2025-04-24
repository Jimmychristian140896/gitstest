package com.jimmy.basecompose.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class RecentSearchManager(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "recent_searches")
    private val RECENT_SEARCH_KEY = stringPreferencesKey("recent_searches")

    suspend fun saveSearch(query: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[RECENT_SEARCH_KEY]?.split("|")?.toMutableList() ?: mutableListOf()
            current.remove(query)
            current.add(0, query)
            if (current.size > 10) current.removeAt(current.lastIndex)
            prefs[RECENT_SEARCH_KEY] = current.joinToString("|")
        }
    }

    suspend fun deleteSearch(query: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[RECENT_SEARCH_KEY]?.split("|")?.toMutableList() ?: mutableListOf()
            current.remove(query)
            prefs[RECENT_SEARCH_KEY] = current.joinToString("|")
        }
    }

    suspend fun getRecentSearches(): List<String> {
        val prefs = context.dataStore.data.first()
        return prefs[RECENT_SEARCH_KEY]?.split("|") ?: emptyList()
    }

    suspend fun clearSearches() {
        context.dataStore.edit { it.remove(RECENT_SEARCH_KEY) }
    }
}