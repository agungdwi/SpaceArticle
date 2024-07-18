package com.compose.spacearticle.domain.usecase

import androidx.paging.PagingData
import com.compose.spacearticle.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleUseCase {
    fun getArticles(newsSite: String?, title: String?): Flow<PagingData<Article>>
    fun getRecentSearch(): Flow<List<Article>>
    suspend fun insertRecentSearch(articles: Article)
    suspend fun deleteAllRecentSearch()
}