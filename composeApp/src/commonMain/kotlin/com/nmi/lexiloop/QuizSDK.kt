package com.nmi.lexiloop

import com.nmi.lexiloop.cache.Database
import com.nmi.lexiloop.cache.DatabaseDriverFactory
import com.nmi.lexiloop.cache.Quiz
import com.nmi.lexiloop.entity.AnswerEntity
import com.nmi.lexiloop.entity.CompleteQuizEntity
import com.nmi.lexiloop.entity.QuizEntity
import com.nmi.lexiloop.network.QuizApi
import com.nmi.lexiloop.util.NetworkError
import com.nmi.lexiloop.util.Result

class QuizSDK(databaseDriverFactory: DatabaseDriverFactory, val api: QuizApi) {
    private val database = Database(databaseDriverFactory)


    @Throws(Exception::class)
    suspend fun getAllQuizzes(forceReload: Boolean): Result<List<CompleteQuizEntity>, NetworkError> {
        val cachedQuizzes = database.getCompleteQuizzes()
        return if (cachedQuizzes.isNotEmpty() && !forceReload) {
            Result.Success(cachedQuizzes)
        } else {
            api.getAllQuizzes().also {
//                database.clearAndCreateQuizzes(it)
            }
        }
    }


//    @Throws(Exception::class)
//    suspend fun getAllQuizzes(forceReload: Boolean): List<CompleteQuizEntity> {
//        val cachedQuizzes = database.getCompleteQuizzes()
//        return if (cachedQuizzes.isNotEmpty() && !forceReload) {
//            cachedQuizzes
//        } else {
//            api.getAllQuizzes().also {
////                database.clearAndCreateQuizzes(it)
//            }
//        }
//    }

    @Throws(Exception::class)
    suspend fun getAllQuizzesCache(): List<QuizEntity> {
        return database.getAllQuizzes()
    }

    @Throws(Exception::class)
    suspend fun getAllCompleteQuizzesCache(): List<CompleteQuizEntity> {
        return database.getCompleteQuizzes()
    }

    @Throws(Exception::class)
    suspend fun insertQuiz(quizId: Long, typeId: Long, quizText: String) {
        val result = database.insertQuiz(quizId, typeId, quizText)
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