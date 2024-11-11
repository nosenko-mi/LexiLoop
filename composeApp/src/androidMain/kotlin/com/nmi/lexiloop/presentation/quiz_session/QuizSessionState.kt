package com.nmi.lexiloop.presentation.quiz_session

import com.nmi.lexiloop.model.quiz.BasicQuiz
import com.nmi.lexiloop.model.quiz.SimpleAnswerModel
import com.nmi.lexiloop.model.quiz.SimpleQuizModel

data class QuizSessionState(
    val quizzes: MutableList<BasicQuiz> = mockList,
    val currentQuizIndex: Int = 0,
    val quizSessionCompletion: Float = 0f,
    val showFeedbackDialog: Boolean = false,
    val points: Int = 0
)


val mockList: MutableList<BasicQuiz> = mutableListOf(
    SimpleQuizModel(
        text = "Question _ 1. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu",
        answers = listOf(
            SimpleAnswerModel(text = "Answer 1 (correct)", isCorrect = true, isChecked = false),
            SimpleAnswerModel(text = "Answer 2", isCorrect = false, isChecked = false),
            SimpleAnswerModel(text = "Answer 3", isCorrect = false, isChecked = false),
            SimpleAnswerModel(
                text = "A bit long answer 4 Lorem ipsum dolor sit amet",
                isCorrect = false,
                isChecked = false
            ),
        )
    ),
    SimpleQuizModel(
        text = "Question _ 2. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu",
        answers = listOf(
            SimpleAnswerModel(text = "Answer 1 (correct)", isCorrect = true, isChecked = false),
            SimpleAnswerModel(text = "Answer 2", isCorrect = false, isChecked = false),
            SimpleAnswerModel(text = "Answer 3", isCorrect = false, isChecked = false),
            SimpleAnswerModel(text = "A bit long answer 4", isCorrect = false, isChecked = false),
        )
    ),
    SimpleQuizModel(
        text = "Question _ 3. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu",
        answers = listOf(
            SimpleAnswerModel(text = "Answer 1 (correct)", isCorrect = true, isChecked = false),
            SimpleAnswerModel(text = "Answer 2", isCorrect = false, isChecked = false),
            SimpleAnswerModel(text = "Answer 3", isCorrect = false, isChecked = false),
            SimpleAnswerModel(text = "A bit long answer 4", isCorrect = false, isChecked = false),
        )
    ),
)