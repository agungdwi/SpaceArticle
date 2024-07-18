package com.compose.spacearticle

import android.app.Application
import com.compose.spacearticle.data.di.databaseModule
import com.compose.spacearticle.data.di.networkModule
import com.compose.spacearticle.data.di.remoteModule
import com.compose.spacearticle.presenter.di.useCaseModule
import com.compose.spacearticle.presenter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    remoteModule,
                    useCaseModule,
                    viewModelModule,
                    databaseModule
                )
            )
        }
    }
}