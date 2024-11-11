package com.nmi.lexiloop.presentation.quiz_session.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nmi.lexiloop.presentation.ui.theme.cornerInnerRadius
import com.nmi.lexiloop.presentation.ui.theme.corners
import com.nmi.lexiloop.presentation.ui.theme.spacing

@Preview
@Composable
fun QuizFeedbackSheet(
    modifier: Modifier = Modifier,
    title: String = "Title",
    bodyText: String = "Body text",
    actionText: String = "Action",
    containerColor: Color = MaterialTheme.colorScheme.background,
    onAction: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(MaterialTheme.corners.medium)
            )
            .background(color = containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(title, style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Text(bodyText)
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Button(
                onClick = onAction,
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    MaterialTheme.corners.medium.cornerInnerRadius(
                        outerThickness = MaterialTheme.spacing.medium
                    )
                )
            ) {
                Text(text = actionText)
            }
        }
    }
}