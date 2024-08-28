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
import com.nmi.lexiloop.record.AudioRecorder
import com.nmi.lexiloop.util.NetworkError
import com.nmi.lexiloop.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

// context injection
// https://stackoverflow.com/questions/53439111/how-to-inject-application-context-from-app-module-to-network-module-using-ko
class BasicScreenViewModel(
    private val sdk: QuizSDK,
    private val recorder: AudioRecorder
) : ViewModel() {
    private val _state = mutableStateOf(BasicScreenState())
    val state: State<BasicScreenState> = _state

    init {
//        loadCompleteQuizzes() // crashes app
        srModel.load()
    }

    fun recognize6Seconds(){
        viewModelScope.launch {
            _state.value = _state.value.copy(recognizedText = "recognizing...", isRecording = true)
            val buffer = recorder.recordXSeconds(6)
            val text = srModel.runInference(buffer)
            _state.value = _state.value.copy(recognizedText = text, isRecording = false)
        }
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
                val completeQuizzes = sdk.getAllQuizzes(forceReload = true)
                when (completeQuizzes){
                    is Result.Error -> {
                        Log.d("BasicScreenViewModel", "loadCompleteQuiz ERROR: ${completeQuizzes.error}")
                        _state.value =
                            _state.value.copy(errorMessage = completeQuizzes.error, isLoading = false, completeQuizzes = emptyList())
                    }
                    is Result.Success -> {
                        Log.d("BasicScreenViewModel", "loadCompleteQuiz SUCCESS")
                        _state.value =
                            _state.value.copy(errorMessage = null, isLoading = false, completeQuizzes = completeQuizzes.data)
                    }
                }
            } catch (e: Exception) { // possible coroutine bug
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
                            id=answerId,
                            quizId = quizId,
                            text = "random answer $answerId",
                            isCorrect = i == randomIndex
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
    val recognizedText: String = "init value",
    val errorMessage: NetworkError? = null
)