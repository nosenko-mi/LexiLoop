package com.nmi.lexiloop.model

abstract class BasicQuiz {
    abstract val type: QuizType
    abstract val text: String
}

data class SimpleQuizModel(
    override val type: QuizType,
    override val text: String,
    val answers: List<SimpleAnswerModel>
) : BasicQuiz() {
    override fun toString(): String {
        return "Quiz:\n$text\nAnswers:\n${answers.toList()}"
    }
}

data class SequenceQuizModel(
    override val type: QuizType,
    override val text: String,
    val answers: List<SequenceAnswerModel>
) : BasicQuiz() {
    override fun toString(): String {
        return "Quiz:\n$text\nAnswers:\n${answers.toList()}"
    }
}

data class VoiceQuizModel(
    override val type: QuizType,
    override val text: String,
    val expectedText: String
) : BasicQuiz() {
    override fun toString(): String {
        return "Quiz:\n$text\nExpected answer:\n$expectedText"
    }
}

//@Serializable
//data class QuizEntity(
//    @SerialName("id")
//    val id: Long,
//    @SerialName("type_id")
//    val typeId: Long,
//    @SerialName("text")
//    val text: String,
////    @SerialName("answers")
////    val answers: List<Answer>
//) : AbstractQuiz()
//
//
//@Serializable
//data class CompleteQuizEntity(
//    @SerialName("id")
//    val id: Long,
//    @SerialName("type_id")
//    val typeId: Long,
//    @SerialName("text")
//    val text: String,
//    @SerialName("answers")
//    val answers: List<AnswerEntity>
//) : AbstractQuiz()
//
//@Serializable
//data class VoiceQuizEntity(
//    @SerialName("id")
//    val id: Long,
//    @SerialName("type_id")
//    val typeId: Long,
//    @SerialName("text")
//    val text: String,
//    @SerialName("expected_text")
//    val expectedText: String,
//) : AbstractQuiz()
//
//
//@Serializable
//data class SequenceQuizEntity(
//    @SerialName("id")
//    val id: Long,
//    @SerialName("type_id")
//    val typeId: Long,
//    @SerialName("text")
//    val text: String,
//    @SerialName("answers")
//    val answers: List<SequenceAnswerEntity>
//) : AbstractQuiz()