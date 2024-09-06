package com.nmi.lexiloop.model.quiz

import com.nmi.lexiloop.util.TextConstants

abstract class BasicQuiz {
    abstract val type: QuizType
    abstract val text: String

    abstract fun getBlankPosition(): Int
    abstract fun isUserResponseCorrect(): Boolean
    abstract fun getCorrectAnswer(): Answer
    abstract fun getUserResponse(): Answer
}

data class SimpleQuizModel(
    override val text: String,
    val answers: List<SimpleAnswerModel>
) : BasicQuiz() {
    override val type: QuizType
        get() = QuizType.Simple

    override fun getBlankPosition(): Int {
        for (i in text.indices) {
            if (text[i].toString() == TextConstants.BLANK_POSITION_CHAR) return i
        }
        return -1
    }

    override fun isUserResponseCorrect(): Boolean {
        answers.forEach { answer ->
            if (!answer.isUserResponseCorrect()) {
                return false
            }
        }
        return true
    }

    override fun getCorrectAnswer(): Answer {
        // when data fetched from api list of answers contain only one correct entry
        val correctAnswer = answers.filter { it.isCorrect }.take(1)
        return correctAnswer[0]
    }

    override fun getUserResponse(): Answer {
        // multiple answers are not allowed in SimpleQuiz
        val correctAnswer = answers.filter { it.isChecked }.take(1)
        return correctAnswer[0]
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

    override fun isUserResponseCorrect(): Boolean {
        answers.forEach { answer ->
            if (!answer.isUserResponseCorrect()) {
                return false
            }
        }
        return true
    }

    override fun getCorrectAnswer(): Answer {
        TODO("Not yet implemented")
    }

    override fun getUserResponse(): Answer {
        TODO("Not yet implemented")
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

    override fun isUserResponseCorrect(): Boolean {
        return text == expectedText
    }

    override fun getCorrectAnswer(): Answer {
        TODO("Not yet implemented")
    }

    override fun getUserResponse(): Answer {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "Quiz:\n$text\nExpected answer:\n$expectedText"
    }
}
