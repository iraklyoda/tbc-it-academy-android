package com.iraklyoda.userssocialapp.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iraklyoda.userssocialapp.presentation.theme.Dimens
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme
import com.iraklyoda.userssocialapp.presentation.utils.CollectSideEffect


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit
) {

    CollectSideEffect(flow = viewModel.sideEffect) { effect ->
        when(effect) {
            is HomeSideEffect.NavigateToProfile -> navigateToProfile()
        }

    }

    HomeScreenContent(
        onEvent = { homeEvent ->
            viewModel.onEvent(event = homeEvent)
        }
    )
}

@Composable
fun HomeScreenContent(
    onEvent: (HomeEvent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Header
            Text(
                text = "Home",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.SpacingMedium),
                textAlign = TextAlign.Center
            )

            // Image Button (Top-Right)
            IconButton(
                onClick = {
                    onEvent(HomeEvent.ProfileBtnClicked)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd).padding(Dimens.SpacingMedium)
                    .size(52.dp)// Position at top-right
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle, // Change to your preferred icon
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    UsersSocialAppTheme {
        HomeScreenContent(
            onEvent = {}
        )
    }
}