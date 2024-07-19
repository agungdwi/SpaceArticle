package com.compose.spacearticle.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.compose.spacearticle.data.local.LocalDataSource
import com.compose.spacearticle.data.remote.network.ApiService
import com.compose.spacearticle.domain.model.Article
import com.compose.spacearticle.domain.repository.IArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleRepository (
    private val apiService: ApiService,
    private val localDataSource: LocalDataSource
) : IArticleRepository {

    override fun getArticles(newsSite: String?, title: String?): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { ArticlePagingSource(apiService, newsSite, title) }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                DataMapper.mapResponseToDomain(response)
            }
        }
    }

    override fun getRecentSearch(): Flow<List<Article>> {
        return localDataSource.getAllArticles().map { entity ->
            entity.map {
                DataMapper.mapEntityToDomain(it)
            }
        }
    }

    override suspend fun insertRecentSearch(articles: Article) {
        val entities =  DataMapper.mapDomainToEntity(articles)
        return localDataSource.insertArticles(entities)
    }

    override suspend fun deleteAllRecentSearch(){
        return localDataSource.deleteAllRecent()
    }

}