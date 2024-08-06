package com.nmi.lexiloop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmi.lexiloop.cache.Quiz
import com.nmi.lexiloop.entity.QuizEntity
import kotlinx.coroutines.launch

class BasicScreenViewModel(private val sdk: QuizSDK) : ViewModel() {
    private val _state = mutableStateOf(BasicScreenState())
    val state: State<BasicScreenState> = _state

    init {
        loadQuizzes()
    }

    fun loadQuizzes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, quizzes = emptyList())
            try {
                val quizzes = sdk.getAllQuizzes(forceReload = true)
                _state.value = _state.value.copy(isLoading = false, quizzes = quizzes)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, quizzes = emptyList())
            }
        }
    }

    fun insertSequence(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, quizzes = emptyList())
            try {
                sdk.insertQuiz(1L, "quiz 1")
                sdk.insertQuiz(2L, "quiz 2")
                sdk.insertQuiz(3L, "quiz 3")

                val quizzes = sdk.getAllQuizzesCache()
                _state.value = _state.value.copy(isLoading = false, quizzes = quizzes)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, quizzes = emptyList())
            }
        }
    }
}

data class BasicScreenState(
    val isLoading: Boolean = false,
    val quizzes: List<QuizEntity> = emptyList()
)