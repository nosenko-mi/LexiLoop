package com.nmi.lexiloop.presentation.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.nmi.lexiloop.model.quiz.BasicQuiz
import com.nmi.lexiloop.model.quiz.QuizType
import com.nmi.lexiloop.model.quiz.SimpleQuizModel
import com.nmi.lexiloop.presentation.ui.theme.spacing


@Composable
fun QuizFeedbackCard(
    modifier: Modifier = Modifier,
    quiz: BasicQuiz,
) {
    when (quiz.type) {
        QuizType.Sequence -> {}
        QuizType.Simple -> SimpleQuizFeedbackCard(
            modifier = modifier,
            quiz = quiz as SimpleQuizModel
        )

        QuizType.Voice -> {}
    }
}


@Composable
fun SimpleQuizFeedbackCard(
    modifier: Modifier = Modifier,
    quiz: SimpleQuizModel,
) {
    val correctColor = Color.hsl(161f, 1f, .2f, 1f)
    val incorrectColor = Color.hsl(344f, 1f, .12f, 1f)
    OutlinedCard(
        onClick = { },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .padding(MaterialTheme.spacing.medium)
        ) {
            val correctAnswer = quiz.getCorrectAnswer()
            val userCheckedText = quiz.getUserResponse()
            if (quiz.isUserResponseCorrect()) {
                FormattedFilledQuizText(
                    text = quiz.text,
                    replacePosition = quiz.getBlankPosition(),
                    replacementWord = correctAnswer.text,
                    color = correctColor,
                    style = MaterialTheme.typography.titleMedium
                )
            } else {

                FormattedFilledQuizText(
                    text = quiz.text,
                    replacePosition = quiz.getBlankPosition(),
                    replacementWord = userCheckedText.text,
                    color = incorrectColor,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier.size(MaterialTheme.spacing.medium))
                Row {
                    Text(
                        buildAnnotatedString {
                            append(userCheckedText.text)
                            append(" ➡\uFE0F ")
                            withStyle(
                                style = SpanStyle(
                                    color = correctColor,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(correctAnswer.text)
                            }

                        }
                    )

                }
            }

        }
    }
}

