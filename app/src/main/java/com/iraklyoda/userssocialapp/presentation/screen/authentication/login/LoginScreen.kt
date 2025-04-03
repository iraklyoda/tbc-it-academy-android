package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.component.AuthTextField
import com.iraklyoda.userssocialapp.presentation.component.MyButton
import com.iraklyoda.userssocialapp.presentation.component.MyCircularProgress
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

    LaunchedEffect(email, password) {
        viewModel.onEvent(event = LoginEvent.SetCredentials(email = email, password = password))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LoginContent(
            state = viewModel.state,
            uiState = viewModel.uiState,
            onEvent = { loginEvent ->
                viewModel.onEvent(loginEvent)
            }
        )

        if (viewModel.state.loader) {
            MyCircularProgress()
        }
    }
}

@Composable
fun LoginContent(
    state: LoginState,
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.SpacingLarger)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(Dimens.SpacingXXL))

            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Dimens.SpacingLarge))


            // Image
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1.2f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vector_login),
                    contentDescription = "Register Illustration",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

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
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = Icons.Default.Email,
                    error = state.emailError
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
                        label = "Password",
                        keyboardType = KeyboardType.Password,
                        leadingIcon = Icons.Default.Lock,
                        error = state.passwordError,
                        isPassword = true
                    )

                    // Remember Me Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = uiState.rememberMe,
                            onCheckedChange = { isChecked ->
                                onEvent(LoginEvent.RememberMeChanged(isChecked)) // Handle checkbox change
                            },
                        )
                        Text(
                            stringResource(R.string.remember_me),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        onEvent(LoginEvent.RememberMeChanged(rememberMe = !uiState.rememberMe))
                                    },
                                )
                                .padding(start = 8.dp),
                        )
                    }
                }

                // Login Button
                MyButton(
                    onClick = { onEvent(LoginEvent.Submit) },
                    isEnabled = state.isLoginBtnEnabled,
                    text = stringResource(R.string.login)
                )

                // Register Btn
                MyButton(
                    onClick = { onEvent(LoginEvent.RegisterBtnClicked) },
                    text = stringResource(R.string.register)
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenContentPreview() {
    UsersSocialAppTheme {
        LoginContent(
            state = LoginState(),
            uiState = LoginUiState(),
            onEvent = {}
        )
    }
}