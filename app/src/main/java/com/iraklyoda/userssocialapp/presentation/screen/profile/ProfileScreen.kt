package com.iraklyoda.userssocialapp.presentation.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.component.ButtonComponent
import com.iraklyoda.userssocialapp.presentation.theme.Dimens
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme
import com.iraklyoda.userssocialapp.presentation.utils.CollectSideEffect

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {

    CollectSideEffect(flow = viewModel.sideEffect) { effect ->
        when (effect) {
            is ProfileSideEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    ProfileContent(
        state = viewModel.state,
        onEvent = { profileEvent ->
            viewModel.onEvent(profileEvent)
        }
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.SpacingMedium)
    ) {
        state.userEmail?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical =
                        Dimens.SpacingLarge
                    ),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.profile_welcome_hello, it),
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        ButtonComponent(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = Dimens.SpacingLarge),
            onClick = { onEvent(ProfileEvent.LogOutBtnClicked) },
            text = stringResource(R.string.logout)
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    UsersSocialAppTheme {
        ProfileContent(
            state = ProfileState(userEmail = "Iraki@Gmail.com"),
            onEvent = {}
        )
    }
}