package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.component.AuthTextField
import com.iraklyoda.userssocialapp.presentation.component.ButtonComponent
import com.iraklyoda.userssocialapp.presentation.component.MyCircularProgress
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.component.RememberMeCheckbox
import com.iraklyoda.userssocialapp.presentation.theme.Dimens
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme
import com.iraklyoda.userssocialapp.presentation.utils.CollectSideEffect

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    email: String,
    password: String,
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val context = LocalContext.current

    CollectSideEffect(flow = viewModel.sideEffect) { effect ->
        when (effect) {
            is LoginSideEffect.NavigateToHome -> {
                navigateToHomeScreen()
            }

            is LoginSideEffect.ShowAuthError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()

            is LoginSideEffect.NavigateToRegister -> {
                navigateToRegisterScreen()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(event = LoginEvent.SetCredentials(email = email, password = password))
    }

    val scrollState = rememberScrollState()

    LoginContent(
        state = viewModel.state,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        scrollState = scrollState
    )

    if (viewModel.state.loader) {
        MyCircularProgress()
    }

}

@Composable
fun LoginContent(
    state: LoginState,
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    scrollState: ScrollState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.SpacingLarger)
            .verticalScroll(scrollState),
    ) {

        Spacer(modifier = Modifier.height(Dimens.SpacingXXL))

        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(Dimens.SpacingLarge))


        // Image
        Image(
            painter = painterResource(id = R.drawable.vector_login),
            contentDescription = "Register Illustration",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth(0.6f)
                .aspectRatio(1.2f),
        )

        Spacer(modifier = Modifier.height(Dimens.SpacingLarge))

        // Form
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Email
            AuthTextField(
                value = uiState.email,
                onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                label = stringResource(R.string.email),
                keyboardType = KeyboardType.Email,
                leadingIcon = Icons.Default.Email,
                errorResource = state.emailErrorResource
            )

            Column {
                // Password
                AuthTextField(
                    value = uiState.password,
                    onValueChange = {
                        onEvent(
                            LoginEvent.PasswordChanged(
                                password = it,
                            )
                        )
                    },
                    label = stringResource(R.string.password),
                    keyboardType = KeyboardType.Password,
                    leadingIcon = Icons.Default.Lock,
                    errorResource = state.passwordErrorResource,
                    isPassword = true,
                    isPasswordVisible = uiState.passwordVisible,
                    onTrailingIconClick = { onEvent(LoginEvent.TogglePasswordVisibility) }
                )

                // Remember Me Checkbox
                RememberMeCheckbox(
                    checkedState = uiState.rememberMe,
                    onToggle = { onEvent(LoginEvent.ToggleRememberMe) }
                )
            }

            // Login Button
            ButtonComponent(
                onClick = { onEvent(LoginEvent.Submit) },
                isEnabled = state.isLoginBtnEnabled,
                text = stringResource(R.string.login)
            )

            // Register Btn
            ButtonComponent(
                onClick = { onEvent(LoginEvent.NavigateToRegister) },
                text = stringResource(R.string.register)
            )

        }
    }
}

@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
fun LoginScreenContentPreview() {
    UsersSocialAppTheme {
        LoginContent(
            state = LoginState(),
            uiState = LoginUiState(),
            onEvent = {},
            scrollState = rememberScrollState()
        )
    }
}