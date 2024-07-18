package com.compose.spacearticle.presenter.di

import com.compose.spacearticle.domain.usecase.ArticleInteractor
import com.compose.spacearticle.domain.usecase.ArticleUseCase
import com.compose.spacearticle.presenter.home.MainViewModel
import com.compose.spacearticle.presenter.recent.RecentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ArticleUseCase> { ArticleInteractor(get())}
}

val viewModelModule = module {
    viewModel{MainViewModel(get())}
    viewModel{ RecentViewModel(get()) }
}