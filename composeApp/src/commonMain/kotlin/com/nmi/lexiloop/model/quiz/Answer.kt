package com.nmi.lexiloop.model.quiz

abstract class Answer {

    abstract val text: String

    abstract fun isUserResponseCorrect(): Boolean
}

class SimpleAnswerModel(
    override val text: String,
    val isCorrect: Boolean,
    var isChecked: Boolean = false,
) : Answer() {
    override fun isUserResponseCorrect(): Boolean {
        return isChecked == isCorrect
    }

    override fun toString(): String {
        return "text=$text; isCorrect = $isCorrect; isChecked = $isChecked"
    }
}

class SequenceAnswerModel(
    override val text: String,
    val position: Int,
    var userSetPosition: Int = -1
) : Answer() {
    override fun isUserResponseCorrect(): Boolean {
        return userSetPosition == position
    }
}
