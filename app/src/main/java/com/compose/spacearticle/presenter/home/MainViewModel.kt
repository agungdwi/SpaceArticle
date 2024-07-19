package com.compose.spacearticle.presenter.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.compose.spacearticle.domain.model.Article
import com.compose.spacearticle.domain.usecase.ArticleUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val articleUseCase: ArticleUseCase) : ViewModel() {

    private val _articles = MutableLiveData<PagingData<Article>>(PagingData.empty())
    val articles: LiveData<PagingData<Article>> get() = _articles

    private val _newsSite = MutableLiveData("")
    val newsSite: LiveData<String> get() = _newsSite

    private val _title = MutableLiveData("")
    val title: LiveData<String> get() = _title

    private val _addRecent = MutableLiveData(false)

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            articleUseCase.getArticles(_newsSite.value, _title.value)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _articles.value = addRecentIfTrue(pagingData)
                }
        }
    }

    private suspend fun addRecentIfTrue(pagingData: PagingData<Article>): PagingData<Article> {
        if (_addRecent.value == true) {
            var isFirstItem = true
            return pagingData.map { article ->
                if (isFirstItem) {
                    isFirstItem = false
                    articleUseCase.insertRecentSearch(article)
                }
                article
            }
        }
        return pagingData
    }

    fun onNewSiteChanges(newsSite: String) {
        _newsSite.value = newsSite
        _addRecent.value = _title.value?.isNotBlank()
        fetchArticles()
    }

    fun onSearch(title: String) {
        _title.value = title
        _addRecent.value = _title.value?.isNotBlank()
        fetchArticles()
    }

}