package com.nmi.lexiloop.presentation.quiz_session

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nmi.lexiloop.model.quiz.BasicQuiz
import com.nmi.lexiloop.model.quiz.QuizType
import com.nmi.lexiloop.model.quiz.SequenceQuizModel
import com.nmi.lexiloop.model.quiz.SimpleQuizModel
import com.nmi.lexiloop.model.quiz.VoiceQuizModel

class QuizSessionViewModel() : ViewModel() {
    private val _state = mutableStateOf(QuizSessionState())
    val state: State<QuizSessionState> = _state

    fun getCurrentQuiz(): BasicQuiz {
        return _state.value.quizzes[_state.value.currentQuizIndex]
    }

    fun processQuiz() {
        val currentQuiz = getCurrentQuiz()
        when (currentQuiz.type) {
            QuizType.Sequence -> {
                //
            }

            QuizType.Simple -> {
                processSimpleQuiz(currentQuiz)
            }

            QuizType.Voice -> { //coroutine scope
                // record
                // recognize
                // update current quiz
                // currentQuiz.getUserResponse().isUserResponseCorrect()
            }
        }
    }

    /**
     * Hides feedback and goes to next quiz.
     */
    fun completeQuiz() {
        if (_state.value.showFeedbackDialog) {
            updateShowFeedback(false)
        }
        nextQuiz()
    }

    fun completeSession() {
        // navigate to separate feedback screen
    }

    fun updateCurrentQuiz(quiz: SimpleQuizModel) {
        Log.d(TAG, "Update quizzes with new quiz: $quiz")
        _state.value = _state.value.copy(
            quizzes = _state.value.quizzes.apply {
                this[_state.value.currentQuizIndex] = quiz
            }
        )
        processQuiz()
    }

    fun updateCurrentQuiz(quiz: VoiceQuizModel) {

    }

    fun updateCurrentQuiz(quiz: SequenceQuizModel) {

    }

    fun updateShowFeedback(value: Boolean) {
        _state.value = _state.value.copy(showFeedbackDialog = value)
    }

    private fun updateQuizSessionCompletion() {
        val completion: Float =
            (_state.value.currentQuizIndex.toFloat() / _state.value.quizzes.size.toFloat()) * 100f
        _state.value = _state.value.copy(
            quizSessionCompletion = completion
        )
    }

    private fun processSimpleQuiz(currentQuiz: BasicQuiz) {
        if (currentQuiz.isUserResponseCorrect()) {
            _state.value = _state.value.copy(
                showFeedbackDialog = false,
                points = _state.value.points + 1
            )
            completeQuiz()
            Log.d(TAG, "Correct answer. points++")
        } else {
            _state.value = _state.value.copy(
                showFeedbackDialog = true,
            )
            Log.d(TAG, "Wrong answer")
        }
    }

    private fun nextQuiz() {
        if (_state.value.currentQuizIndex == _state.value.quizzes.lastIndex) {
            Log.d(TAG, "nextQuiz: complete session.")
            completeSession()

        } else {
            Log.d(TAG, "nextQuiz: update currentQuizIndex.")
            _state.value = _state.value.copy(
                currentQuizIndex = _state.value.currentQuizIndex + 1
            )
        }
        updateQuizSessionCompletion()
    }


    companion object {
        const val TAG = "QuizSessionViewModel"
    }

}