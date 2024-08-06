package com.nmi.lexiloop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Text(text = "android basic screen")
    }
}