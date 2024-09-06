package com.nmi.lexiloop.presentation.feedback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmi.lexiloop.presentation.common.TwoButtonsBottomBar
import com.nmi.lexiloop.presentation.feedback.components.QuizFeedbackListItem
import com.nmi.lexiloop.presentation.feedback.components.QuizSessionScoreComponent
import com.nmi.lexiloop.presentation.ui.theme.spacing
import com.nmi.lexiloop.util.calculateCurrentSize
import com.nmi.lexiloop.util.getFraction
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedbackScreen() {
    val viewModel = koinViewModel<FeedbackScreenViewModel>()
    val state by remember { viewModel.state }

    val lazyListState = rememberLazyListState()
    val maxScroll: Float
    with(LocalDensity.current){
        maxScroll = 30.dp.toPx()
    }

    val currentOffset by remember {
        derivedStateOf {
            val scrollOffset = lazyListState.firstVisibleItemScrollOffset
            if (lazyListState.firstVisibleItemIndex != 0){
                maxScroll
            } else{
                scrollOffset.toFloat().coerceIn(0f, maxScroll)
            }
        }
    }

    var maximumScroll: Float
    var toolbarExpandedHeight: Float
    var toolbarCollapsedHeight: Float

    var toolbarTextSizeExpanded: Float
    var toolbarTextSizeCollapsed: Float

    with(LocalDensity.current) {
        maximumScroll = 32.dp.toPx()

        toolbarCollapsedHeight = 42.dp.toPx()
        toolbarExpandedHeight = toolbarCollapsedHeight * 6

        toolbarTextSizeCollapsed = 24.sp.toPx()
        toolbarTextSizeExpanded = 96.sp.toPx()
    }

    var toolbarHeight: Dp
    var toolbarHeightPx: Float
    var toolbarTextSize: TextUnit
    var fraction: Float
    with(LocalDensity.current) {
        fraction = getFraction(maximumScroll, currentOffset)
        toolbarHeightPx = calculateCurrentSize(toolbarCollapsedHeight, toolbarExpandedHeight, fraction)
        toolbarHeight = toolbarHeightPx.toDp()
        toolbarTextSize = calculateCurrentSize(toolbarTextSizeCollapsed, toolbarTextSizeExpanded, fraction).toSp()
    }

    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                QuizSessionScoreComponent(
                    componentHeight = toolbarHeight,
                    textSize = toolbarTextSize,
                )
                LazyColumn(
                    state = lazyListState
                ) {
                    items(state.quizzes) {
                        QuizFeedbackListItem(quiz = it)
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = MaterialTheme.spacing.extraSmall,
                                    horizontal = 0.dp
                                )
                        )
                    }
                    item{
                        Spacer(modifier = Modifier.size(MaterialTheme.spacing.extraLarge))

                    }
                }
            }

            TwoButtonsBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                primaryButtonText = "Go home",
                secondaryIconVector = Icons.Default.Replay,
                onPrimaryClick = {},
                onSecondaryClick = {}
            )
        }
    }
//    1/3 of a screen
//    Score component. On scroll shrink to top to look like top app bar.

//    rate this session (quiz selection, not user feelings). Scroll as usual.
//    😟😐😀
//    or use stars

//    Feedback for each quiz

//    always visible on the bottom line:
//    Small repeat Icon button + Go home button
}

