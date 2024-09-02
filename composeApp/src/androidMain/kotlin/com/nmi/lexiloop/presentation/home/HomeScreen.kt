package com.nmi.lexiloop.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nmi.lexiloop.model.quiz.QuizType
import com.nmi.lexiloop.presentation.common.StartCompilationQuizCard
import com.nmi.lexiloop.presentation.common.StartQuizCard
import com.nmi.lexiloop.presentation.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val state by remember { viewModel.state }
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        "",
                    )
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }

        ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = 0.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    "Hi, ${state.user.displayName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.large))
                StartCompilationQuizCard(
                    text = "Start quiz",
                    icon = Icons.Default.AllInclusive,
                    modifier = Modifier
                ) { }
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

                StartQuizCard(text = "Start quiz", quizType = QuizType.Simple, modifier = Modifier) { }
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

                StartQuizCard(text = "Start quiz", quizType = QuizType.Voice, modifier = Modifier) { }
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

                StartQuizCard(
                    text = "Start quiz",
                    quizType = QuizType.Sequence,
                    modifier = Modifier
                ) { }
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

            }
        }
    }
}