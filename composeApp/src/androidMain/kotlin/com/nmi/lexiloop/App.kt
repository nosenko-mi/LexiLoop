package com.nmi.lexiloop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nmi.lexiloop.entity.CompleteQuizEntity
import com.nmi.lexiloop.entity.QuizEntity
import org.koin.androidx.compose.koinViewModel


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun App() {
    val viewModel = koinViewModel<BasicScreenViewModel>()
    val state by remember { viewModel.state }
    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        viewModel.loadQuizzes()
        pullToRefreshState.endRefresh()
    }
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            if (state.quizzes.isNotEmpty()){
                Quizzes(state.quizzes)
            }

            if (state.completeQuizzes.isNotEmpty()){
                CompleteQuizzes(state.completeQuizzes)
            }

            Button(onClick = { viewModel.insertSequence() }) {
                Text(text = "insert sequence")
            }

            Button(onClick = { viewModel.loadCompleteQuizzes() }) {
                Text(text = "complete quizzes")
            }
        }
    }
}

@Composable
fun Quizzes(quizzes: List<QuizEntity>) {
    LazyColumn {
        items(quizzes){ q->
            Text("${q.id}, ${q.text}")
        }
    }
}

@Composable
fun CompleteQuizzes(quizzes: List<CompleteQuizEntity>) {
    LazyColumn {
        items(quizzes){ q->
            Text("${q.quiz.id}: ${q.answers.forEach { it.text }}")
        }
    }
}