package com.nmi.lexiloop

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.nmi.lexiloop.entity.QuizEntity
import com.nmi.lexiloop.model.QuizType
import com.nmi.lexiloop.model.SimpleQuizModel
import com.nmi.lexiloop.presentation.common.StartQuizCard
import org.koin.androidx.compose.koinViewModel


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class
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
    val context = LocalContext.current
    val recordPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO) // experimental
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
//            record audio then recognize
        } else {
            viewModel.updatePermissionDialogVisibility(true)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            if (state.permissionDialogVisible) {
                AlertDialog(
                    dialogTitle = "Record audio",
                    dialogText = "permission is required",
                    icon = Icons.Default.Info,
                    dismissText = "dismiss",
                    confirmText = "confirm",
                    onDismissRequest = {
                        viewModel.updatePermissionDialogVisibility(
                            false
                        )
                    },
                    onConfirmation = {
                        openAppSettings(context)
                        viewModel.updatePermissionDialogVisibility(false)
                    },
                )
            }

//            Text(text = state.recognizedText)
//
//            Button(onClick = {
//                if (state.isRecording) {
//                    // stop recognition or smt
//                    viewModel.stopRecognition()
//                    return@Button
//                }
//                when (recordPermissionState.status) {
//                    PermissionStatus.Granted -> {
//                        // start record
//
//                        viewModel.recognize6Seconds()
//                    }
//                    else -> {
//                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
//                    }
//                }
//            }) {
//                Text(text = "recognize")
//            }

//            if (state.quizzes.isNotEmpty()){
//                Quizzes(state.quizzes)
//            }
//
            if (state.completeQuizzes.isNotEmpty()){
                CompleteQuizzes(state.completeQuizzes)
            }

            state.errorMessage?.let {
                Text(
                    text = it.name,
                    color = Color.Red
                )
            }

//            Button(onClick = { viewModel.insertRandomCompleteQuiz() }) {
//                Text(text = "insert random quiz")
//            }

            StartQuizCard(text = "Start quiz", quizType = QuizType.Simple, modifier = Modifier) { }
            StartQuizCard(text = "Start quiz", quizType = QuizType.Voice, modifier = Modifier) { }
            StartQuizCard(text = "Start quiz", quizType = QuizType.Sequence, modifier = Modifier) { }

            Button(onClick = { viewModel.loadCompleteQuizzes() }) {
                Text(text = " get complete quizzes")
            }
        }
    }
}

@Composable
fun Quizzes(quizzes: List<QuizEntity>) {
    LazyColumn {
        items(quizzes) { q ->
            Text("${q.id}, ${q.text}")
        }
    }
}

@Composable
fun CompleteQuizzes(quizzes: List<SimpleQuizModel>) {
    LazyColumn {
        items(quizzes) { q ->
            Text(q.toString())
        }
    }
}

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector? = null,
//    dismissText: String = stringResource(id = R.string.dismiss_btn),
//    confirmText: String = stringResource(id = R.string.confirm_btn),
    dismissText: String = "dismiss",
    confirmText: String = "confirm",
) {
    androidx.compose.material3.AlertDialog(
        icon = {
            icon?.let { Icon(it, contentDescription = "Example Icon") }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(dismissText)
            }
        }
    )
}

fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}