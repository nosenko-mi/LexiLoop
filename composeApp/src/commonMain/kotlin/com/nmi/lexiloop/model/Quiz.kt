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
