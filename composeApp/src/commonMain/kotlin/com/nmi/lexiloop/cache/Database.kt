package com.nmi.lexiloop.cache

import com.nmi.lexiloop.entity.AnswerEntity
import com.nmi.lexiloop.entity.CompleteQuizEntity
import com.nmi.lexiloop.entity.QuizEntity

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val driver = databaseDriverFactory.createDriver()
    private val database = QuizDatabase(driver)
    private val dbQuery = QuizDatabaseQueries(driver)

    internal fun getAllQuizzes(): List<QuizEntity> {
        return dbQuery.selectAllQuizzes(::mapQuiz).executeAsList()
    }

    internal fun clearAndCreateQuizzes(quizzes: List<QuizEntity>) {
        dbQuery.transaction {
            dbQuery.removeAllQuizzes()
            quizzes.forEach { quiz ->
                dbQuery.insertQuiz(
                    id = quiz.id,
                    quiz_text = quiz.text
                )
            }
        }
    }

    internal fun getAllAnswers(): List<AnswerEntity> {
        return dbQuery.selectAllAnswers(::mapAnswer).executeAsList()
    }

    internal fun getCompleteQuizzes(): List<CompleteQuizEntity> {
        val quizzes = dbQuery.selectAllQuizzes(::mapQuiz).executeAsList()
        val answers = dbQuery.selectAllAnswers(::mapAnswer).executeAsList().groupBy { it.quizId }

        val completeQuizzes: MutableList<CompleteQuizEntity> = mutableListOf()
        quizzes.forEach { quiz ->
            val a = answers.getOrElse(quiz.id) { null }
            if (a != null) {
                completeQuizzes.add(CompleteQuizEntity(quiz, a))
            }
        }

        return completeQuizzes
    }

    internal fun insertQuiz(id: Long, text: String): Result<Unit> {
        val result = dbQuery.runCatching {
            dbQuery.insertQuiz(id, text)
        }
        return result
    }

    internal fun insertAnswer(
        id: Long,
        text: String,
        isCorrect: Boolean,
        quizId: Long
    ): Result<Unit> {
        val result = dbQuery.runCatching {
            dbQuery.insertAnswer(id, text, isCorrect, quizId)
        }
        return result
    }

    internal fun insertCompleteQuiz(quiz: QuizEntity, answers: List<AnswerEntity>) {
        dbQuery.transaction {
            dbQuery.insertQuiz(quiz.id, quiz.text)
            answers.forEach {
                dbQuery.insertAnswer(it.id, it.text, it.isCorrect, it.quizId)
            }
        }
    }

    private fun mapQuiz(
        id: Long,
        text: String,
    ): QuizEntity {
        return QuizEntity(id, text)
    }

    private fun mapAnswer(
        id: Long,
        text: String,
        isCorrect: Boolean,
        quizId: Long
    ): AnswerEntity {
        return AnswerEntity(id, quizId, text, isCorrect)
    }

}