package com.jimmy.basecompose.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jimmy.basecompose.data.local.entity.ArticleEntity
import com.jimmy.basecompose.domain.model.ArticleType

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE type = :type ORDER BY publishedAt DESC")
    suspend fun getArticlesByType(type: ArticleType): List<ArticleEntity>

    @Upsert
    suspend fun upsertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE type = :type")
    suspend fun deleteArticlesByType(type: ArticleType)

}