package com.nmi.lexiloop

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmi.lexiloop.entity.AnswerEntity
import com.nmi.lexiloop.entity.CompleteQuizEntity
import com.nmi.lexiloop.entity.QuizEntity
import com.nmi.lexiloop.ml.srModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.random.Random

// context injection
// https://stackoverflow.com/questions/53439111/how-to-inject-application-context-from-app-module-to-network-module-using-ko
class BasicScreenViewModel(private val sdk: QuizSDK) : ViewModel() {
    private val _state = mutableStateOf(BasicScreenState())
    val state: State<BasicScreenState> = _state

    init {
        srModel.load()
    }

    fun recognize(){
        _state.value = _state.value.copy(recognizedText = "recognizing...", isRecording = true)
    }

    fun stopRecognition(){
        _state.value = _state.value.copy(recognizedText = "stopped", isRecording = false)
    }

    fun updatePermissionDialogVisibility(visibility: Boolean){
        _state.value = _state.value.copy(permissionDialogVisible = visibility)
    }

    fun loadQuizzes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, quizzes = emptyList())
            try {
//                val quizzes = sdk.getAllQuizzes(forceReload = true)
                val quizzes = sdk.getAllQuizzesCache()
                _state.value = _state.value.copy(isLoading = false, quizzes = quizzes)
            } catch (e: Exception) {
                Log.e("BasicScreenViewModel", e.printStackTrace().toString())
                _state.value = _state.value.copy(isLoading = false, quizzes = emptyList())
            }
        }
    }

    fun loadCompleteQuizzes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true, completeQuizzes = emptyList())
            try {
                val completeQuizzes = sdk.getAllCompleteQuizzesCache()
                _state.value =
                    _state.value.copy(isLoading = false, completeQuizzes = completeQuizzes)
            } catch (e: Exception) {
                Log.e("BasicScreenViewModel", e.printStackTrace().toString())
                _state.value = _state.value.copy(isLoading = false, completeQuizzes = emptyList())
            }
        }
    }

    fun insertRandomCompleteQuiz() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, completeQuizzes = emptyList())
            try {
                val quizId = System.currentTimeMillis()
                val answers: MutableList<AnswerEntity> = mutableListOf()
                val randomIndex = Random.nextInt(0, 4)
                for (i in 1..4) {
                    val answerId = System.currentTimeMillis()
                    answers.add(
                        AnswerEntity(
                            answerId,
                            quizId,
                            "random answer $answerId",
                            i == randomIndex
                        )
                    )
                }
                sdk.insertCompleteQuiz(
                    QuizEntity(quizId, "random quiz $quizId"),
                    answers
                )
                val completeQuizzes = sdk.getAllCompleteQuizzesCache()
                _state.value =
                    _state.value.copy(isLoading = false, completeQuizzes = completeQuizzes)
            } catch (e: Exception) {
                Log.e("BasicScreenViewModel", e.printStackTrace().toString())
                _state.value = _state.value.copy(isLoading = false, completeQuizzes = emptyList())
            }
        }
    }
}

data class BasicScreenState(
    val isLoading: Boolean = false,
    val quizzes: List<QuizEntity> = emptyList(),
    val completeQuizzes: List<CompleteQuizEntity> = emptyList(),
    val permissionDialogVisible: Boolean = false,
    val isRecording: Boolean = false,
    val recognizedText: String = "init value"
)