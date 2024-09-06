package com.nmi.lexiloop.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.nmi.lexiloop.presentation.ui.theme.spacing

@Composable
fun TwoButtonsBottomBar(
    modifier: Modifier = Modifier,
    primaryButtonText: String,
    secondaryIconVector: ImageVector,
    secondaryIconContentDescription: String = "",
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        IconButton(onClick = onSecondaryClick) {
            Icon(secondaryIconVector, secondaryIconContentDescription)
        }
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.large))
        Button(onClick = onPrimaryClick) {
            Text(text = primaryButtonText)
        }
    }
}