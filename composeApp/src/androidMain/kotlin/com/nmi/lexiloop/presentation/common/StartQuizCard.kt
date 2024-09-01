package com.nmi.lexiloop.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nmi.lexiloop.model.QuizType
import com.nmi.lexiloop.presentation.ui.theme.spacing

@Preview
@Composable
fun StartQuizCard(
    modifier: Modifier = Modifier,
    text: String = "Title text",
    quizType: QuizType = QuizType.Simple,
    onClick: () -> Unit = {}
) {
    OutlinedCard(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        // Apply padding to Box children only.
        // Icon in background should be offset
        Box(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(0.dp, 175.dp)
                .widthIn(0.dp, 320.dp) // not sure about 320
                .clip(RectangleShape)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
                    .align(
                        Alignment.TopStart
                    )
                    .padding(MaterialTheme.spacing.medium)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = text,
                modifier = modifier
                    .align(
                        Alignment.BottomEnd
                    )
                    .padding(MaterialTheme.spacing.medium)

            )
            Icon(
                imageVector = getCorrectIcon(quizType = quizType),
                contentDescription = "",
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .alpha(0.05f)
                    .size(256.dp)
                    .absoluteOffset(x = 64.dp, y = (-32).dp) // questionable position, ok for now

            )
        }
    }
}

@Composable
fun getCorrectIcon(quizType: QuizType): ImageVector {
    return when (quizType) {
        QuizType.Sequence -> Icons.Default.Share
        QuizType.Simple -> Icons.AutoMirrored.Filled.MenuBook
        QuizType.Voice -> Icons.Default.RecordVoiceOver
    }
}