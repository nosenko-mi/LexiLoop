package com.nmi.lexiloop.presentation.quiz_session

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.nmi.lexiloop.model.quiz.QuizType
import com.nmi.lexiloop.model.quiz.SimpleQuizModel
import com.nmi.lexiloop.presentation.quiz.sequence_quiz.SequenceQuizScreen
import com.nmi.lexiloop.presentation.quiz.simple_quiz.SimpleQuizScreen
import com.nmi.lexiloop.presentation.quiz.voice_quiz.VoiceQuizScreen
import com.nmi.lexiloop.presentation.quiz_session.components.QuizFeedbackSheet
import com.nmi.lexiloop.presentation.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizSessionScreen(
    onCloseClick: () -> Unit,
) {
    val viewModel = koinViewModel<QuizSessionViewModel>()
    val state by remember { viewModel.state }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = String.format(
                            Locale.ENGLISH,
                            "%.0f%%  ",
                            state.quizSessionCompletion
                        ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.medium)
                .fillMaxSize()
        ) {
            Spacer(Modifier.size(MaterialTheme.spacing.medium))
            when (state.quizzes[state.currentQuizIndex].type) {
                QuizType.Sequence -> {
                    SequenceQuizScreen()
                }

                QuizType.Simple -> {
                    SimpleQuizScreen(
                        quiz = state.quizzes[state.currentQuizIndex] as SimpleQuizModel,
                        onClick = viewModel::updateCurrentQuiz
                    )
                }

                QuizType.Voice -> {
                    VoiceQuizScreen()
                }
            }
            if (state.showFeedbackDialog) {
                QuizFeedbackSheet(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    title = "Depends on answer",
                    bodyText = "depends as well",
                    actionText = "Next",
                ) {
                    viewModel.completeQuiz()
                }
            }

        }

    }
}