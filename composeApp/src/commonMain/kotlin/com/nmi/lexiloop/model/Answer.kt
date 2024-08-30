package com.nmi.lexiloop.model


data class SimpleAnswerModel(
    val text: String,
    val isCorrect: Boolean,
)

data class SequenceAnswerModel(
    val text: String,
    val position: Int,
)