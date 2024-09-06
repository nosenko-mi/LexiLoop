package com.nmi.lexiloop.presentation.feedback

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FeedbackScreenViewModel(
) : ViewModel() {
    private val _state = mutableStateOf(FeedbackScreenState())
    val state: State<FeedbackScreenState> = _state


    fun updateIsInTopPosition(value: Boolean){
        _state.value = _state.value.copy(isScoreInTopPosition = value)
    }
}