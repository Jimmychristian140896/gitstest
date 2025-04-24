package com.jimmy.basecompose.core.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.data.local.datastore.SessionManager
import com.jimmy.basecompose.domain.repository.AuthRepository
import com.jimmy.basecompose.domain.repository.SessionRepository
import com.jimmy.basecompose.navigation.Route
import org.koin.java.KoinJavaComponent.inject


@Composable
fun WithSessionCheck(
    navController: NavHostController,
    destinationIfLoggedOut: Route = Route.Login,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isSessionValid by remember { mutableStateOf<Boolean?>(null) }

    val authRepository: AuthRepository by inject(AuthRepository::class.java)
    val sessionRepository: SessionRepository by inject(SessionRepository::class.java)

    LaunchedEffect(Unit) {
        sessionRepository.getLoginTime()
            .onSuccess { loginTime->
                val isExpired = loginTime == null || (System.currentTimeMillis() - loginTime) > 10 * 60 * 1000
                isSessionValid = !isExpired

                if (isExpired) {
                    authRepository.logout()
                    navController.navigate(destinationIfLoggedOut) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

    }

    if (isSessionValid == true) {
        content()
    }
}
