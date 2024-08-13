package com.nmi.lexiloop

import com.nmi.lexiloop.cache.AndroidDatabaseDriverFactory
import com.nmi.lexiloop.network.QuizApi
import com.nmi.lexiloop.record.AndroidAudioRecorder
import com.nmi.lexiloop.record.AudioRecorder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<QuizApi> { QuizApi() }
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
}

