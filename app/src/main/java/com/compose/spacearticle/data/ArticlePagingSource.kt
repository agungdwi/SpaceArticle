package com.compose.spacearticle.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.compose.spacearticle.data.remote.network.ApiService
import com.compose.spacearticle.data.remote.response.ResultsItem


class ArticlePagingSource(
    private val apiService: ApiService,
    private val newsSite: String?,
    private val title: String?
) : PagingSource<String, ResultsItem>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ResultsItem> {
        return try {
            val url = params.key ?: "https://api.spaceflightnewsapi.net/v4/articles/?limit=10"
            val response = apiService.getArticles(url, newsSite, title)

            LoadResult.Page(
                data = response.results ?: emptyList(),
                prevKey = response.previous,
                nextKey = response.next
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, ResultsItem>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}