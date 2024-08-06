package com.nmi.lexiloop.cache

import com.nmi.lexiloop.entity.AnswerEntity
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

    internal fun getCompleteQuizzes() {
        val quizzes = dbQuery.selectAllQuizzes().executeAsList()
        val answers = dbQuery.selectAllAnswers().executeAsList().groupBy { it.quiz_id }


    }

    internal fun insertQuiz(id: Long, text: String) {
        dbQuery.insertQuiz(id, text)
    }

    internal fun insertAnswer(id: Long, text: String, isCorrect: Boolean, quizId: Long) {
        dbQuery.insertAnswer(id, text, isCorrect, quizId)
    }

    internal fun insertCompleteQuiz(quiz: QuizEntity, answers: List<AnswerEntity>){
        dbQuery.transaction {
            dbQuery.insertQuiz(quiz.id, quiz.text)
            answers.forEach {
                dbQuery.insertAnswer(it.id,it.text, it.isCorrect, it.quizId)
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