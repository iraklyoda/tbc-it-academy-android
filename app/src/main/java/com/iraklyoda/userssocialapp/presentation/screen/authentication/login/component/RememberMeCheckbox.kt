package com.iraklyoda.userssocialapp.presentation.screen.authentication.login.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.theme.Dimens

@Composable
fun RememberMeCheckbox(
    checkedState: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                onToggle()
            },
        )
        Text(
            text = stringResource(R.string.remember_me),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        onToggle()
                    },
                )
                .padding(start = Dimens.SpacingSmall),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RememberMeCheckBoxPreview() {
    RememberMeCheckbox(
        checkedState = false,
        onToggle = {}
    )
}