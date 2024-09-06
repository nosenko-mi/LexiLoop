package com.nmi.lexiloop.presentation.feedback.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


@Composable
fun QuizSessionScoreComponent(
    modifier: Modifier = Modifier,
    percentage: Float = 70f,
    componentHeight: Dp,
    textSize: TextUnit
) {
    if (percentage < 0 && percentage > 100) {
        // TODO normalize to range (0..100)
    }
    Surface(
        ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .height(componentHeight)
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            Text(
                "${percentage}%",
                style = MaterialTheme.typography.displayMedium,
                fontSize = textSize,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}