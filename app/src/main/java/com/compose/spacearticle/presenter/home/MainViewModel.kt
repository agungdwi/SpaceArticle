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

class MainViewModel(private val articleUseCase:ArticleUseCase): ViewModel(){


    private val _articles = MutableLiveData<PagingData<Article>>(PagingData.empty())
    val articles: LiveData<PagingData<Article>> get() = _articles

    private val _newsSite = MutableLiveData("")
    val newsSite : LiveData<String> get() = _newsSite

    private val _title = MutableLiveData("")
    val title : LiveData<String> get() = _title

    private val _addRecent = MutableLiveData(false)

    init {
        fetchArticles()
    }

    private fun fetchArticles(newsSite: String? = _newsSite.value, title: String? = _title.value) {
        viewModelScope.launch {
            articleUseCase.getArticles(newsSite, title)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    val transformedData = if (_addRecent.value == true) {
                        insertFirstAsRecent(pagingData)
                    } else {
                        pagingData
                    }
                    _articles.value = transformedData
                }
        }
    }

    private suspend fun insertFirstAsRecent(pagingData: PagingData<Article>): PagingData<Article> {
        var isFirst = true
        return pagingData.map { article ->
            if (isFirst) {
                articleUseCase.insertRecentSearch(article)
                isFirst = false
            }
            article
        }
    }

    fun onNewSiteChanges(newsSite: String){
        _newsSite.value = newsSite
        fetchArticles()
    }

    fun onSearch(title: String, addRecent: Boolean){
        _title.value = title
        _addRecent.value = addRecent
        fetchArticles()
    }

}