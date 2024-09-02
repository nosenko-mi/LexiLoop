package com.nmi.lexiloop.entity

import com.nmi.lexiloop.model.quiz.SimpleAnswerModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json

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
data class SequenceAnswerEntity(
    @SerialName("text")
    val text: String,
    @SerialName("position")
    val position: Int,
    @SerialName("id")
    val id: Long,
    @SerialName("quiz_id")
    val quizId: Long,
)

fun AnswerEntity.toAnswerModel(): SimpleAnswerModel {
    return SimpleAnswerModel(text = this.text, isCorrect = this.isCorrect)
}