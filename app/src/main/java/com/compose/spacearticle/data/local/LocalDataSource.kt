package com.compose.spacearticle.data.local

import com.compose.spacearticle.data.local.entity.ArticleEntity
import com.compose.spacearticle.data.local.room.ArticleDao

class LocalDataSource(private val articleDao: ArticleDao){
    suspend fun insertArticles(articles: ArticleEntity) = articleDao.insertArticles(articles)

    suspend fun deleteAllRecent()= articleDao.deleteAll()

    fun getAllArticles() = articleDao.getAllArticles()
}