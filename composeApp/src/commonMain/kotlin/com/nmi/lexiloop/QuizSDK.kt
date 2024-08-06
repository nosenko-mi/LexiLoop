package com.nmi.lexiloop

import com.nmi.lexiloop.cache.Database
import com.nmi.lexiloop.cache.DatabaseDriverFactory
import com.nmi.lexiloop.cache.Quiz
import com.nmi.lexiloop.entity.QuizEntity
import com.nmi.lexiloop.network.QuizApi

class QuizSDK(databaseDriverFactory: DatabaseDriverFactory, val api: QuizApi) {
    private val database = Database(databaseDriverFactory)


    @Throws(Exception::class)
    suspend fun getAllQuizzes(forceReload: Boolean): List<QuizEntity> {
        val cachedQuizzes = database.getAllQuizzes()
        return if (cachedQuizzes.isNotEmpty() && !forceReload) {
            cachedQuizzes
        } else {
            emptyList()
//            api.getAllLaunches().also {
//                database.clearAndCreateQuizzes(it)
//            }
        }
    }

    @Throws(Exception::class)
    suspend fun getAllQuizzesCache(): List<QuizEntity> {
        return database.getAllQuizzes()
    }


    @Throws(Exception::class)
    suspend fun insertQuiz(quizId: Long, quizText: String) {
        database.insertQuiz(quizId, quizText)
    }
}