package com.nmi.lexiloop

import com.nmi.lexiloop.cache.Database
import com.nmi.lexiloop.cache.DatabaseDriverFactory
import com.nmi.lexiloop.cache.Quiz
import com.nmi.lexiloop.entity.AnswerEntity
import com.nmi.lexiloop.entity.CompleteQuizEntity
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
    suspend fun getAllCompleteQuizzesCache(): List<CompleteQuizEntity> {
        return database.getCompleteQuizzes()
    }

    @Throws(Exception::class)
    suspend fun insertQuiz(quizId: Long, quizText: String) {
        val result = database.insertQuiz(quizId, quizText)
    }

    @Throws(Exception::class)
    suspend fun insertAnswer(id: Long, text: String, isCorrect: Boolean, quizId: Long, ) {
        database.insertAnswer(id, text, isCorrect, quizId)
    }

    @Throws(Exception::class)
    suspend fun insertCompleteQuiz(quiz: QuizEntity, answers: List<AnswerEntity>) {
        database.insertCompleteQuiz(quiz, answers)
    }


}