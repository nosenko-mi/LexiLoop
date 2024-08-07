package com.nmi.lexiloop

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmi.lexiloop.entity.AnswerEntity
import com.nmi.lexiloop.entity.CompleteQuizEntity
import com.nmi.lexiloop.entity.QuizEntity
import kotlinx.coroutines.Dispatchers
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
//                val quizzes = sdk.getAllQuizzes(forceReload = true)
                val quizzes = sdk.getAllQuizzesCache()
                _state.value = _state.value.copy(isLoading = false, quizzes = quizzes)
            } catch (e: Exception) {
                Log.e("BasicScreenViewModel",  e.printStackTrace().toString())
                _state.value = _state.value.copy(isLoading = false, quizzes = emptyList())
            }
        }
    }

    fun loadCompleteQuizzes(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true, quizzes = emptyList())
            try {
                val completeQuizzes = sdk.getAllCompleteQuizzesCache()
                _state.value = _state.value.copy(isLoading = false, completeQuizzes = completeQuizzes)
            } catch (e: Exception) {
                Log.e("BasicScreenViewModel",  e.printStackTrace().toString())
                _state.value = _state.value.copy(isLoading = false, completeQuizzes = emptyList())
            }
        }
    }

    fun insertSequence(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, quizzes = emptyList())
            try {

                sdk.insertQuiz(1L, "quiz 1")
//                sdk.insertQuiz(2L, "quiz 2")
//                sdk.insertQuiz(44L, "quiz 44")
//                sdk.insertAnswer(222, "answer 222", false, 1)
//                sdk.insertAnswer(223, "answer 223", false, 1)
//                sdk.insertAnswer(224, "answer 224", true, 1)
//                sdk.insertAnswer(225, "answer 225", false, 1)
//                sdk.insertAnswer(226, "answer 226", false, 1)

//                sdk.insertCompleteQuiz(
//                    QuizEntity(4L, "complete quiz"),
//                    listOf(
//                        AnswerEntity(1, 4, "answer 1", true),
//                        AnswerEntity(2, 4, "answer 2", false),
//                        AnswerEntity(3, 4, "answer 3", false),
//                        AnswerEntity(3, 4, "answer 3", false),
//                    )
//                )

                val quizzes = sdk.getAllQuizzesCache()
                _state.value = _state.value.copy(isLoading = false, quizzes = quizzes)
            } catch (e: Exception) {
                Log.e("BasicScreenViewModel",  e.printStackTrace().toString())
                _state.value = _state.value.copy(isLoading = false, quizzes = emptyList())
            }
        }
    }
}

data class BasicScreenState(
    val isLoading: Boolean = false,
    val quizzes: List<QuizEntity> = emptyList(),
    val completeQuizzes: List<CompleteQuizEntity> = emptyList(),
)