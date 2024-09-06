package com.nmi.lexiloop.presentation.feedback

import com.nmi.lexiloop.model.quiz.BasicQuiz
import com.nmi.lexiloop.model.quiz.SimpleAnswerModel
import com.nmi.lexiloop.model.quiz.SimpleQuizModel
import com.nmi.lexiloop.model.user.User

data class FeedbackScreenState(
    val user: User = User("john.doe@email.com", "johndoe", "John Doe"),
    val quizzes: List<BasicQuiz> = mockList,
    val completionCount: Float = 0f,
    val isScoreInTopPosition: Boolean = true,
)

val mockList: List<SimpleQuizModel> = listOf(
    SimpleQuizModel(
        text = "Q _ 1", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 2 e", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = false),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = true),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 3", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 3", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 4", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 5", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 6", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 7", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 8", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 9", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Q _ 10", answers = listOf(
            SimpleAnswerModel(text = "a 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "a 2", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Last _ quiz", answers = listOf(
            SimpleAnswerModel(text = "insert 1", isCorrect = true, isChecked = true),
            SimpleAnswerModel(text = "insert 2", isCorrect = false, isChecked = false),
        )
    ),
)