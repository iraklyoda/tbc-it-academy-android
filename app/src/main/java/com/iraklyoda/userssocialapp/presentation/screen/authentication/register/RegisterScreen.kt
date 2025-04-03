package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.iraklyoda.userssocialapp.presentation.theme.Dimens
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme
import com.iraklyoda.userssocialapp.presentation.utils.CollectSideEffect
import com.iraklyoda.userssocialapp.presentation.component.AuthTextField
import com.iraklyoda.userssocialapp.presentation.component.MyCircularProgress


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: (email: String, password: String) -> Unit
) {
    val context = LocalContext.current

    CollectSideEffect(flow = viewModel.sideEffect) { effect ->
        when (effect) {
            is RegisterSideEffect.NavigateToLogin -> {
                navigateToLogin(effect.email, effect.password)
            }

            is RegisterSideEffect.ShowApiError -> Toast.makeText(
                context,
                effect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        RegisterContent(
            state = viewModel.state,
            uiState = viewModel.uiState,
            onEvent = { registerEvent ->
                viewModel.onEvent(registerEvent)
            }
        )

        // Handle Loader State
        if (viewModel.state.loader) {
            MyCircularProgress()
        }
    }
}

@Composable
fun RegisterContent(
    state: RegisterState,
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(Dimens.SpacingXXL))

            Text(
                text = "Register",
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
                    painter = painterResource(id = R.drawable.vector_register),
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
                    onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) },
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = Icons.Default.Email,
                    error = state.emailError
                )

                // Password
                AuthTextField(
                    value = uiState.password,
                    onValueChange = {
                        onEvent(
                            RegisterEvent.PasswordChanged(
                                password = it,
                                repeatedPassword = uiState.repeatedPassword
                            )
                        )
                    },
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    leadingIcon = Icons.Default.Lock,
                    error = state.passwordError,
                    isPassword = true
                )

                // Repeat Password
                AuthTextField(
                    value = uiState.repeatedPassword,
                    onValueChange = {
                        onEvent(
                            RegisterEvent.RepeatedPasswordChanged(
                                repeatedPassword = it,
                                password = uiState.password,
                            )
                        )
                    },
                    label = "Repeat Password",
                    keyboardType = KeyboardType.Password,
                    leadingIcon = Icons.Default.Lock,
                    error = state.repeatedPasswordError,
                    isPassword = true
                )

                // Button
                Button(
                    onClick = {
                        onEvent(
                            RegisterEvent.Submit
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = Dimens.ButtonHeight),
                    enabled = state.isSignUpBtnEnabled
                ) {
                    Text(
                        stringResource(R.string.submit),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    UsersSocialAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            RegisterContent(
                state = RegisterState(),
                uiState = RegisterUiState(),
                onEvent = {}
            )
            if (false) {
                MyCircularProgress()
            }
        }
    }
}


