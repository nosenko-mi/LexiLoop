package com.nmi.lexiloop.entity

import com.nmi.lexiloop.model.QuizType
import com.nmi.lexiloop.model.SimpleQuizModel
import com.nmi.lexiloop.model.toQuizType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json

abstract class AbstractQuiz

@Serializable
data class QuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("type_id")
    val typeId: Long,
    @SerialName("text")
    val text: String,
//    @SerialName("answers")
//    val answers: List<Answer>
) : AbstractQuiz()


@Serializable
data class CompleteQuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("type_id")
    val typeId: Long,
    @SerialName("text")
    val text: String,
    @SerialName("answers")
    val answers: List<AnswerEntity>
) : AbstractQuiz()

@Serializable
data class VoiceQuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("type_id")
    val typeId: Long,
    @SerialName("text")
    val text: String,
    @SerialName("expected_text")
    val expectedText: String,
) : AbstractQuiz()


@Serializable
data class SequenceQuizEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("type_id")
    val typeId: Long,
    @SerialName("text")
    val text: String,
    @SerialName("answers")
    val answers: List<SequenceAnswerEntity>
) : AbstractQuiz()

fun CompleteQuizEntity.toModel(): SimpleQuizModel {
    return SimpleQuizModel(
        type=this.typeId.toQuizType(),
        text = this.text,
        answers = this.answers.map { it.toAnswerModel() }
    )
}