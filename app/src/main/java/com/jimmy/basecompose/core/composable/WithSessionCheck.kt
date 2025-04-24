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
import com.jimmy.basecompose.data.local.datastore.SessionManager
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

    val sessionManager: SessionManager by inject(SessionManager::class.java)

    LaunchedEffect(Unit) {
        val loginTime = sessionManager.getLoginTime(context)
        val isExpired = loginTime == null || (System.currentTimeMillis() - loginTime) > 10 * 60 * 1000
        isSessionValid = !isExpired

        if (isExpired) {
            sessionManager.clearSession(context)
            navController.navigate(destinationIfLoggedOut) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    if (isSessionValid == true) {
        content()
    }
}
