package com.iraklyoda.userssocialapp.presentation.screen.authentication.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreenBackground(
    circleSize: Dp = 182.dp,
    content: @Composable () -> Unit
) {
    AuthScreenBackgroundContent(
        content = content
    )
}

@Composable
fun AuthScreenBackgroundContent(
    circleSize: Dp = 182.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        CircleGradientGraphic(
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.TopStart)
                .offset(x = -(circleSize / 3), y = -(circleSize / 2))
        )

        CircleGradientGraphic(
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.BottomStart)
                .offset(x = -(circleSize / 2), y = circleSize / 2)
        )

        content()
    }
}

@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
fun AuthScreenBackgroundPreview() {
    AuthScreenBackgroundContent(
        content = @Composable() {}
    )
}
