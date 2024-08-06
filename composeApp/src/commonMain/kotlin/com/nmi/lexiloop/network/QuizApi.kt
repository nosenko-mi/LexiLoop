package com.nmi.lexiloop.network

import com.nmi.lexiloop.entity.QuizEntity
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class QuizApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

//    suspend fun getAllLaunches(): List<CompleteQuizModel> {
//        return httpClient.get("https://...").body()
//    }
    suspend fun getAllLaunches(): List<QuizEntity> {
        return emptyList()
    }
}