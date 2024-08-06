package com.nmi.lexiloop

import com.nmi.lexiloop.cache.AndroidDatabaseDriverFactory
import com.nmi.lexiloop.network.QuizApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<QuizApi> { QuizApi() }
    single<QuizSDK> {
        QuizSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ), api = get()
        )
    }
    viewModel { BasicScreenViewModel(sdk = get()) }
}

