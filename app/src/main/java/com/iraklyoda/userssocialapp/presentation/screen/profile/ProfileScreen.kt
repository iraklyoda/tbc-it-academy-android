package com.iraklyoda.userssocialapp.presentation.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {

    ProfileContent(
        navigateToLogin = navigateToLogin
    )

}

@Composable
fun ProfileContent(
    navigateToLogin: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { navigateToLogin() }
        ) {
            Text(text = "Log Out")
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    UsersSocialAppTheme {
        ProfileContent(
            navigateToLogin = {}
        )
    }
}