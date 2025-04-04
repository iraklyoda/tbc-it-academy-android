package com.iraklyoda.userssocialapp.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iraklyoda.userssocialapp.presentation.theme.Dimens

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    text: String = "",
) {
    Button(
        modifier = modifier
            .then(
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.ButtonHeight)
            ),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}