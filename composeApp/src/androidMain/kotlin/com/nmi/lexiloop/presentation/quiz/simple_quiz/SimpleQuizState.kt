package com.nmi.lexiloop.presentation.quiz.simple_quiz

import com.nmi.lexiloop.model.quiz.SimpleAnswerModel
import com.nmi.lexiloop.model.quiz.SimpleQuizModel

data class QuizSessionState(
    val quiz: SimpleQuizModel = mockQuiz,
)

val mockQuiz = SimpleQuizModel(
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
)

fun mockFun(){
}