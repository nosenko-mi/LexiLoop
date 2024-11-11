package com.nmi.lexiloop.presentation.quiz.simple_quiz

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nmi.lexiloop.model.quiz.SimpleQuizModel
import com.nmi.lexiloop.presentation.ui.theme.corners
import com.nmi.lexiloop.presentation.ui.theme.spacing


@Preview
@Composable
fun SimpleQuizScreen(
    quiz: SimpleQuizModel = mockQuiz,
    onClick: (updatedQuiz: SimpleQuizModel) -> Unit = {} // update parent vm
) {
    val selectedOption = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .selectableGroup(),
        ) {
            // Quiz text
            Text(text = quiz.text, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.size(MaterialTheme.spacing.medium))
            // Block of answers
            quiz.answers.forEach { answer ->
                SingleRadioButtonAnswer(
                    modifier = Modifier.fillMaxWidth(),
                    text = answer.text,
                    selected = (answer.text == selectedOption.value),
                    onClick = {
                        selectedOption.value = answer.text
                        Log.d("QuizSession", "Selected value ${answer.text}")
                    })
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                UserAction(modifier = Modifier.align(Alignment.BottomCenter), text = "Next", selectedOption = selectedOption.value, onClick = {
                    quiz.answers.forEach { answer ->
                        if (answer.text == selectedOption.value) {
                            answer.isChecked = true
                        } else {
                            answer.isChecked = false
                        }
                    }
                    selectedOption.value = ""
                    onClick(quiz)
                })
            }
        }
    }
}

@Composable
fun UserAction(
    modifier: Modifier = Modifier,
    text: String,
    selectedOption: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val isActive = selectedOption != ""
    val content =
        if (isPressed) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
    val background =
        if (isPressed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val border =
        if (isPressed) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary

    OutlinedButton(
        modifier = modifier,
        onClick = {
            onClick()
        },
        enabled = isActive,
        interactionSource = interactionSource,
        border = BorderStroke(
            2.dp,
            border
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = content
        ),
        shape = RoundedCornerShape(MaterialTheme.corners.medium),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SingleRadioButtonAnswer(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(MaterialTheme.corners.medium),
        border = BorderStroke(
            2.dp,
            if (selected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = {} // does not fire, leave empty
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}