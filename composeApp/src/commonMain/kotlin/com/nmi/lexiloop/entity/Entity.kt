package com.nmi.lexiloop.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json

@Serializable
data class QuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
//    @SerialName("answers")
//    val answers: List<Answer>
)

@Serializable
data class AnswerEntity(
    @SerialName("text")
    val text: String,
    @SerialName("is_correct")
    val isCorrect: Boolean,
    @SerialName("id")
    val id: Long,
    @SerialName("quiz_id")
    val quizId: Long,
)


@Serializable
data class CompleteQuizEntity (
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
    @SerialName("answers")
    val answers: List<AnswerEntity>
)


