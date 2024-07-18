package com.compose.spacearticle.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.spacearticle.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: ArticleEntity)

    @Query("SELECT * FROM recent_search")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM recent_search")
    suspend fun deleteAll()

}