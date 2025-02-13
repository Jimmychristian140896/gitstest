package com.jimmy.basecompose.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jimmy.basecompose.core.composable.ObserveAsEvents
import com.jimmy.basecompose.core.composable.ValidationTextField
import com.jimmy.basecompose.core.ui.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    ObserveAsEvents(viewModel.eventChannel) { event ->
        when(event) {
            is LoginEvent.OnLoginFailed -> {

            }
            LoginEvent.OnLoginSuccess -> {
                context.showToast("Login Success")
            }
        }
    }
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        LoginScreen(state = state, onAction = viewModel::onAction)
        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
            )
        }
    }
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValidationTextField(
            state.username,
            onValueChange = {
                onAction(LoginAction.OnUsernameChanged(it))
            },
            hint = "Username",
            isValid = false,
            modifier = Modifier,
        )
        Spacer(Modifier.height(16.dp))
        ValidationTextField(
            state.password,
            onValueChange = {
                onAction(LoginAction.OnPasswordChanged(it))
            },
            hint = "Password",
            isValid = false,
            modifier = Modifier,
        )
        /*val usernameState = rememberTextFieldState(state.username)
        LaunchedEffect(usernameState.text) {
            onAction(LoginAction.OnUsernameChanged(usernameState.text.toString()))
        }
        BasicTextField(
            state = usernameState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            decorator = { innerTextField ->
                if (state.username.isEmpty()) {
                    Text(text = "Username")
                }
                innerTextField()
            }
        )*/
        /*val passwordState = rememberTextFieldState(state.password)
        LaunchedEffect(passwordState.text) {
            onAction(LoginAction.OnPasswordChanged(passwordState.text.toString()))
        }
        BasicTextField(
            state = passwordState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            decorator = { innerTextField ->
                if (state.password.isEmpty()) {
                    Text(text = "Password")
                }
                innerTextField()
            }
        )*/
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                onAction(LoginAction.OnLoginClicked)
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text("Login")
        }
    }
}