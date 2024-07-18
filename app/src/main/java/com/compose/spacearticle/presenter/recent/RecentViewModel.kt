package com.compose.spacearticle.presenter.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.compose.spacearticle.domain.usecase.ArticleUseCase
import kotlinx.coroutines.launch

class RecentViewModel (private val articleUseCase: ArticleUseCase): ViewModel(){

    val recentArticle = articleUseCase.getRecentSearch().asLiveData()

    fun deleteAll(){
        viewModelScope.launch {
            articleUseCase.deleteAllRecentSearch()
        }

    }


}