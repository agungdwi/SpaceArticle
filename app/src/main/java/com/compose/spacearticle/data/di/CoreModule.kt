package com.compose.spacearticle.data.di

import androidx.room.Room
import com.compose.spacearticle.data.ArticleRepository
import com.compose.spacearticle.data.local.LocalDataSource
import com.compose.spacearticle.data.local.room.ArticleDatabase
import com.compose.spacearticle.data.remote.network.ApiService
import com.compose.spacearticle.domain.repository.IArticleRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module{

    single {
        Room.databaseBuilder(
            androidContext(),
            ArticleDatabase::class.java, "article.db"
        ). fallbackToDestructiveMigration().build()
    }

    single { get<ArticleDatabase>().articleDao() }
}

val remoteModule = module{
    single{LocalDataSource(get())}
    single<IArticleRepository>{ArticleRepository(get(), get ())}
}