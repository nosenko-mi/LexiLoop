package com.nmi.lexiloop.presentation.feedback.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun FormattedFilledQuizText(
    text: String,
    replacePosition: Int,
    replacementWord: String,
    color: Color,
    style: TextStyle
) {
    Text(
        buildAnnotatedString {
            append(text.substring(0, replacePosition))
            append(" ")
            withStyle(style = SpanStyle(color = color, fontWeight = FontWeight.Bold)) {
                append(replacementWord)
            }
            append(" ")
            append(text.substring(replacePosition + 1, text.length))
        },
        style = style
    )
}