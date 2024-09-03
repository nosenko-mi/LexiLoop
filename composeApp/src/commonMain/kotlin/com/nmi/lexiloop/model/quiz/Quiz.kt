package com.nmi.lexiloop.model.quiz

abstract class BasicQuiz {
    abstract val type: QuizType
    abstract val text: String

    abstract fun getBlankPosition(): Int
}

data class SimpleQuizModel(
    override val text: String,
    val answers: List<SimpleAnswerModel>
) : BasicQuiz() {
    override val type: QuizType
        get() = QuizType.Simple

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
    override val text: String,
    val answers: List<SequenceAnswerModel>
) : BasicQuiz() {
    override val type: QuizType
        get() = QuizType.Sequence

    override fun getBlankPosition(): Int {
        return -1
    }

    override fun toString(): String {
        return "Quiz:\n$text\nAnswers:\n${answers.toList()}"
    }
}

data class VoiceQuizModel(
    override val text: String,
    val expectedText: String
) : BasicQuiz() {
    override val type: QuizType
        get() = QuizType.Voice

    override fun getBlankPosition(): Int {
        return -1
    }

    override fun toString(): String {
        return "Quiz:\n$text\nExpected answer:\n$expectedText"
    }
}
