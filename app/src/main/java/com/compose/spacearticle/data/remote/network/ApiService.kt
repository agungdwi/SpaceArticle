package com.compose.spacearticle.data.remote.network

import com.compose.spacearticle.data.remote.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getArticles(
        @Url url: String,
        @Query("news_site") newsSite: String? = null,
        @Query("title_contains") title: String? = null
    ): ArticleResponse
}