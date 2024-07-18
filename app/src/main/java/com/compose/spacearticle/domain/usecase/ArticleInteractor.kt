package com.compose.spacearticle.domain.usecase

import androidx.paging.PagingData
import com.compose.spacearticle.domain.model.Article
import com.compose.spacearticle.domain.repository.IArticleRepository
import kotlinx.coroutines.flow.Flow

class ArticleInteractor (
    private val articleRepository: IArticleRepository
) : ArticleUseCase{
    override fun getArticles(newsSite: String?, title: String?) = articleRepository.getArticles(newsSite, title)
    override fun getRecentSearch(): Flow<List<Article>> = articleRepository.getRecentSearch()

    override suspend fun insertRecentSearch(articles: Article) = articleRepository.insertRecentSearch(articles)
    override suspend fun deleteAllRecentSearch() = articleRepository.deleteAllRecentSearch()

}