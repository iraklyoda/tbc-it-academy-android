package com.iraklyoda.userssocialapp.presentation.screen.authentication.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircleGradientGraphic(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = minOf(canvasWidth, canvasHeight) / 2f
        val center = Offset(canvasWidth / 2f, canvasHeight / 2f)

        val angleInRadians = Math.toRadians(155.02)
        val endX = center.x + cos(angleInRadians).toFloat() * radius
        val endY = center.y + sin(angleInRadians).toFloat() * radius

        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0x9993E1D3),
                    Color(0x00E2E2E2)
                ),
                start = center,
                end = Offset(endX, endY)
            ),
            radius = radius,
            center = center
        )
    }
}


@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun CircleGradientGraphicPreview() {
    CircleGradientGraphic()
}