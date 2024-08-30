package com.nmi.lexiloop.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json

abstract class AbstractQuiz

@Serializable
data class QuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
//    @SerialName("answers")
//    val answers: List<Answer>
) : AbstractQuiz()


@Serializable
data class CompleteQuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
    @SerialName("answers")
    val answers: List<AnswerEntity>
) : AbstractQuiz()

@Serializable
data class VoiceQuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
    @SerialName("expected_text")
    val expectedText: String,
) : AbstractQuiz()


@Serializable
data class SequenceQuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
    @SerialName("answers")
    val answers: List<SequenceAnswerEntity>
) : AbstractQuiz()