package com.nmi.lexiloop.di

import com.nmi.lexiloop.BasicScreenViewModel
import com.nmi.lexiloop.QuizSDK
import com.nmi.lexiloop.cache.AndroidDatabaseDriverFactory
import com.nmi.lexiloop.network.QuizApi
import com.nmi.lexiloop.network.createHttpClient
import com.nmi.lexiloop.presentation.home.HomeScreenViewModel
import com.nmi.lexiloop.record.AndroidAudioRecorder
import com.nmi.lexiloop.record.AudioRecorder
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<QuizApi> { QuizApi(createHttpClient(OkHttp.create())) }
    single<AudioRecorder> {
        AndroidAudioRecorder(context=androidContext())
    }
    single<QuizSDK> {
        QuizSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ), api = get()
        )
    }
    viewModel { BasicScreenViewModel(sdk = get(), recorder = get()) }
    viewModel { HomeScreenViewModel(sdk = get()) }
}

