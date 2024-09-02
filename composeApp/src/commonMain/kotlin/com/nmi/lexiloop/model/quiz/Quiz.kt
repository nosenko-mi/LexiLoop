package com.nmi.lexiloop.model.quiz

abstract class BasicQuiz {
    abstract val type: QuizType
    abstract val text: String

    abstract fun getBlankPosition(): Int
}

data class SimpleQuizModel(
    override val type: QuizType,
    override val text: String,
    val answers: List<SimpleAnswerModel>
) : BasicQuiz() {

    override fun getBlankPosition(): Int {
        for (i in text.indices){
            if (text[i] == '_') return i
        }
        return -1
    }

    override fun toString(): String {
        return "Quiz:\n$text\nAnswers:\n${answers.toList()}"
    }
}

data class SequenceQuizModel(
    override val type: QuizType,
    override val text: String,
    val answers: List<SequenceAnswerModel>
) : BasicQuiz() {

    override fun getBlankPosition(): Int {
        return -1
    }

    override fun toString(): String {
        return "Quiz:\n$text\nAnswers:\n${answers.toList()}"
    }
}

data class VoiceQuizModel(
    override val type: QuizType,
    override val text: String,
    val expectedText: String
) : BasicQuiz() {

    override fun getBlankPosition(): Int {
        return -1
    }

    override fun toString(): String {
        return "Quiz:\n$text\nExpected answer:\n$expectedText"
    }
}
