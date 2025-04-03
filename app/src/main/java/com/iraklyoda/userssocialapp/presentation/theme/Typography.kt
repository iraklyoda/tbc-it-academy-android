package com.iraklyoda.userssocialapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.iraklyoda.userssocialapp.R

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium)
)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontSize = Dimens.BodyLarge,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
    ),
    titleLarge = TextStyle(
        fontSize = Dimens.TitleLarge,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold
    )
)
