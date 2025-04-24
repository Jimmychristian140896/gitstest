package com.jimmy.basecompose.data.local.converter

import androidx.room.TypeConverter
import com.jimmy.basecompose.data.local.entity.AuthorEntity
import com.jimmy.basecompose.data.local.entity.EventEntity
import com.jimmy.basecompose.data.local.entity.LaunchEntity
import com.jimmy.basecompose.domain.model.Author
import com.jimmy.basecompose.domain.model.Event
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ArticleConverter {

    @TypeConverter
    fun fromAuthorsList(authors: List<AuthorEntity>?): String? {
        return authors?.mapNotNull { Json.encodeToString(it) }?.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun toAuthorsList(authorsString: String?): List<AuthorEntity>? {
        return authorsString?.split(SEPARATOR)?.mapNotNull { if(it.isEmpty()) null else Json.decodeFromString(it.trim()) }
    }
    @TypeConverter
    fun fromEventList(events: List<EventEntity>?): String? {
        return events?.mapNotNull { Json.encodeToString(it) }?.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun toEventList(eventsString: String?): List<EventEntity>? {
        return eventsString?.split(SEPARATOR)?.mapNotNull { if(it.isEmpty()) null else Json.decodeFromString(it.trim()) }
    }
    @TypeConverter
    fun fromLaunchList(launches: List<LaunchEntity>?): String? {
        return launches?.mapNotNull { Json.encodeToString(it) }?.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun toLaunchList(launchesString: String?): List<LaunchEntity>? {
        return launchesString?.split(SEPARATOR)?.mapNotNull { if(it.isEmpty()) null else Json.decodeFromString(it.trim()) }
    }

    companion object {
        private const val SEPARATOR = "|"
    }
}