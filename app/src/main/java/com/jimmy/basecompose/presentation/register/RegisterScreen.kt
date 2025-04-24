package com.jimmy.basecompose.presentation.register

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jimmy.basecompose.R
import com.jimmy.basecompose.core.composable.ObserveAsEvents
import com.jimmy.basecompose.core.ui.showToast
import com.jimmy.basecompose.core.util.hasNotificationPermission
import com.jimmy.basecompose.navigation.Route
import com.jimmy.basecompose.presentation.login.LoginAction
import com.jimmy.basecompose.presentation.login.LoginEvent
import com.jimmy.basecompose.ui.theme.Green
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    navHostController: NavHostController
) {
    val viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is RegisterEvent.OnRegisterFailed -> {
                context.showToast(event.message.asString(context))

            }
            RegisterEvent.OnRegisterSuccess -> {
                context.showToast("Register Success")
                navHostController.navigate(Route.Home)
            }

            RegisterEvent.NavigateToLogin -> {
                navHostController.navigate(Route.Login)
            }
            is RegisterEvent.OnLoginFailed -> {
                context.showToast(event.message.asString(context))

            }
            RegisterEvent.OnLoginSuccess -> {
                context.showToast("Login Success")
                navHostController.navigate(Route.Home)
            }
            else -> {
            }
        }
    }
    RegisterScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@Preview
@Composable
private fun Preview() {
    RegisterScreen(
        state = RegisterState(),
        onAction = {}
    )

}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onAction(RegisterAction.OnRegisterClicked)
        } else {
            context.showToast("Permission Denied")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.ic_gits_logo),
            contentDescription = "Logo",
            //contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .height(120.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome", fontSize = 28.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.name,
            shape = RoundedCornerShape(4.dp),
            isError = state.nameError != null,
            singleLine = true,
            supportingText = {
                if (state.nameError != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.nameError.asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            onValueChange = { onAction(RegisterAction.OnNameChanged(it)) },
            label = { Text("Name*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.username,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = state.usernameError != null,
            singleLine = true,
            supportingText = {
                if (state.usernameError != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.usernameError.asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            shape = RoundedCornerShape(4.dp),
            onValueChange = { onAction(RegisterAction.OnUsernameChanged(it)) },
            label = { Text("Email address*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = state.passwordError != null,
            supportingText = {
                if (state.passwordError != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.passwordError.asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            shape = RoundedCornerShape(4.dp),
            onValueChange = { onAction(RegisterAction.OnPasswordChanged(it)) },
            label = { Text(stringResource(R.string.password)) },
            trailingIcon = {
                IconButton(onClick = { onAction(RegisterAction.OnPasswordVisibleChange) }) {
                    Icon(
                        imageVector = if (state.passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = stringResource(R.string.toggle_password)
                    )
                }
            },
            visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.repeatPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(4.dp),
            isError = state.repeatPasswordError != null,
            singleLine = true,
            supportingText = {
                if (state.repeatPasswordError != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.repeatPasswordError.asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            onValueChange = { onAction(RegisterAction.OnRepeatPasswordChanged(it)) },
            label = { Text("Repeat Password*") },
            trailingIcon = {
                IconButton(onClick = { onAction(RegisterAction.OnRepeatPasswordVisibleChange) }) {
                    Icon(
                        imageVector = if (state.repeatPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = stringResource(R.string.toggle_password)
                    )
                }
            },
            visualTransformation = if (state.repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if(!state.isLoading) {
                    if (!context.hasNotificationPermission() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        onAction(RegisterAction.OnRegisterClicked)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            //.padding(horizontal = 16.dp, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            if(state.isLoading) {
                CircularProgressIndicator(
                    color = White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Continue",
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Already have an account?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log in",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        onAction(RegisterAction.OnLoginClicked)
                    }
            )
        }
    }
}
