package com.nmi.lexiloop.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Corners(
    val default: Dp = 15.dp,
    val extraSmall: Dp = 5.dp,
    val small: Dp = 10.dp,
    val medium: Dp = 15.dp,
    val large: Dp = 20.dp,
    val extraLarge: Dp = 25.dp,
)

val LocalCorners = compositionLocalOf { Corners() }

val MaterialTheme.corners: Corners
    @Composable
    @ReadOnlyComposable
    get() = LocalCorners.current