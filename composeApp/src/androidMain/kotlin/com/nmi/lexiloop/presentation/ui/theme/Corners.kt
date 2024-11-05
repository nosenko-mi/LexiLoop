package com.nmi.lexiloop.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

data class Corners(
    val default: Int = 35,
    val extraSmall: Int = 5,
    val small: Int = 20,
    val medium: Int = 35,
    val large: Int = 50,
    val extraLarge: Int = 65,
)

val LocalCorners = compositionLocalOf{Corners()}

val MaterialTheme.corners: Corners
    @Composable
    @ReadOnlyComposable
    get() = LocalCorners.current