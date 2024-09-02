package com.nmi.lexiloop.model.quiz

sealed class QuizType {
    data object Simple : QuizType()
    data object Sequence : QuizType()
    data object Voice : QuizType()
}

fun Long.toQuizType(): QuizType {
    return when (this) {
        1L -> QuizType.Simple
        2L -> QuizType.Sequence
        3L -> QuizType.Voice
        else -> QuizType.Simple
    }
}